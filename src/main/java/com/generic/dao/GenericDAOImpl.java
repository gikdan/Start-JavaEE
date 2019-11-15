package com.generic.dao;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;


/**
 * JPA implementation of the GenericDAO.
 *
 * @param <T>  The persistent type
 * @param <ID> The primary key type
 * @author Daniel Mboya
 */
public class GenericDAOImpl<T, ID extends Serializable> implements GenericDAO<T, ID> {

  private final Class<T> persistentClass;

  @SuppressWarnings("unused")
  private Logger log = Logger.getLogger(getClass());

  @PersistenceContext
  private EntityManager em;

  private Session session;

  /**
   * @see GenericDAOImpl#setEm(EntityManager)
   */
  public void setEm(EntityManager em) {
    this.em = em;
    this.session = (Session) this.em.getDelegate();

  }

  /**
   * @see GenericDAOImpl#getEm()
   */
  public EntityManager getEm() {
    return this.em;
  }

  /**
   * @see GenericDAOImpl#getSession()
   */
  @Override
  public Session getSession() {
    return this.session;
  }

  @SuppressWarnings("unchecked")
  public GenericDAOImpl() {
    this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
      .getGenericSuperclass()).getActualTypeArguments()[0];
  }

  public GenericDAOImpl(final Class<T> persistentClass) {
    super();
    this.persistentClass = persistentClass;
  }

  /**
   * @see GenericDAO#getEntityClass()
   */
  public Class<T> getEntityClass() {
    return persistentClass;
  }

  @Override
  public List<T> findByProperty(final int firstResult,
                                final int maxResults, String propertyName, Object value) {
    List<T> result = findByCriteria(firstResult,
            maxResults, Restrictions.eq(propertyName, value));
    return result;
  }


  /**
   * @see GenericDAO#save(Object)
   * #save(java.lang.Object)
   */
  @Override
  public T save(T entity)  {
    //Method method = entity.getClass().getSuperclass().getDeclaredMethod("getId", new AppClass[] {});
    //Long id = (Long) method.invoke(entity, new Object[] {});
    //log.info("in generic dao =====================" + id);
    entity = em.merge(entity);
    return entity;
  }

  /**
   * @see GenericDAO#delete(Object)
   */
  @Override
  public void delete(T entity) {
    em.remove(entity);
  }

  /**
   * @see GenericDAO#deleteById(Serializable)
   */
  @Override
  public void deleteById(final ID id) {
    T entity = em.find(persistentClass, id);
    if (entity != null) em.remove(entity);
  }

  /**
   * @see GenericDAO#delete(Object)
   */
  @Override
  public void delete(ID ids[]){
    int size = ids.length;

    for (int idx = 0; idx < size; idx++) {
      if (ids[idx] != null) {
        T entity = em.find(persistentClass, ids[idx]);
        if (entity != null)
          em.remove(entity);
      }
    }

  }

  /**
   * @see GenericDAO#findById(Serializable)
   */
  @Override
  public T findById(final ID id) {
    final T result = em.find(persistentClass, id);
    return result;
  }

  /**
   * @see GenericDAO#findAll()
   */
  @Override
  public List<T> findAll() {
    return findByCriteria();
  }

  /**
   * @see GenericDAO#countAll()
   */
  @Override
  public int countAll() {
    return countByCriteria();
  }

  /**
   * @see GenericDAO#countByExample(Object)
   */
  @Override
  public int countByExample(final T exampleInstance) {
    Session session = (Session) em.getDelegate();
    Criteria crit = session.createCriteria(getEntityClass());
    crit.setProjection(Projections.rowCount());
    crit.add(Example.create(exampleInstance));

    return (Integer) crit.list().get(0);
  }

  /**
   * @see GenericDAO#findByExample(Object)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findByExample(final T exampleInstance) {
    Session session = (Session) em.getDelegate();
    Criteria crit = session.createCriteria(getEntityClass());
    final List<T> result = crit.list();
    return result;
  }

  /**
   * @see GenericDAO#findByNamedQuery(String, Object...)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findByNamedQuery(final String name, Object... params) {
    javax.persistence.Query query = em.createNamedQuery(name);

    for (int i = 0; i < params.length; i++) {
      query.setParameter(i + 1, params[i]);
    }

    final List<T> result = (List<T>) query.getResultList();
    return result;
  }

  /**
   * @see GenericDAO#findByNamedQuery(String, Map)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findByNamedQuery(final String name,
                                  final Map<String, ? extends Object> params) {
    javax.persistence.Query query = em.createNamedQuery(name);

    for (final Map.Entry<String, ? extends Object> param : params.entrySet()) {
      query.setParameter(param.getKey(), param.getValue());
    }

    final List<T> result = (List<T>) query.getResultList();
    return result;
  }

  /**
   * @see GenericDAO#findByCriteria(int, int, ProjectionList, List, List)
   */
  @Override
  public List<T> findByCriteria(final int firstResult,
                                final int maxResults, final ProjectionList projectionList,
                                final List<Criterion> criterion, final List<String> orderBy) {

    Session session = (Session) em.getDelegate();
    Criteria crit = session.createCriteria(getEntityClass());

    if (projectionList != null)
      crit.setProjection(projectionList);

    for (final Criterion c : criterion) {
      crit.add(c);
    }

    for (final String order : orderBy) {
      crit.addOrder(Order.asc(order));
    }

    if (firstResult > 0) {
      crit.setFirstResult(firstResult);
    }

    if (maxResults > 0) {
      crit.setMaxResults(maxResults);
    }

    @SuppressWarnings("unchecked") final List<T> result = crit.list();

    return result;
  }

  /**
   * @see GenericDAO#findByCriteria(int, int, ProjectionList, Map, List, List)
   */
  @Override
  public List<T> findByCriteria(final int firstResult,
                                final int maxResults, final ProjectionList projectionList,
                                final Map<String, String> aliases,
                                final List<Criterion> criterion, final List<String> orderBy) {

    Session session = (Session) em.getDelegate();
    Criteria crit = session.createCriteria(getEntityClass());

    if (aliases != null)
      for (Map.Entry<String, String> alias : aliases.entrySet()) {
        crit.createAlias(alias.getKey(), alias.getValue());
      }

    if (projectionList != null)
      crit.setProjection(projectionList);

    for (final Criterion c : criterion) {
      crit.add(c);
    }

    if (orderBy != null)
      for (final String order : orderBy) {
        crit.addOrder(Order.asc(order));
      }

    if (firstResult > 0) {
      crit.setFirstResult(firstResult);
    }

    if (maxResults > 0) {
      crit.setMaxResults(maxResults);
    }

    @SuppressWarnings("unchecked") final List<T> result = crit.list();

    return result;
  }

  /**
   * @see GenericDAO#findByCriteria(int, int, ProjectionList, List, Map)
   */
  @Override
  public List<T> findByCriteria(final int firstResult,
                                final int maxResults, final ProjectionList projectionList,
                                final List<Criterion> criterion, final Map<String, String> orderBy) {

    Session session = (Session) em.getDelegate();
    Criteria crit = session.createCriteria(getEntityClass());

    if (projectionList != null)
      crit.setProjection(projectionList);

    for (final Criterion c : criterion) {
      crit.add(c);
    }

    for (final Map.Entry<String, String> order : orderBy.entrySet()) {
      if (order.getValue().equals("desc"))
        crit.addOrder(Order.desc(order.getKey()));
      else
        crit.addOrder(Order.asc(order.getKey()));
    }

    if (firstResult > 0) {
      crit.setFirstResult(firstResult);
    }

    if (maxResults > 0) {
      crit.setMaxResults(maxResults);
    }

    @SuppressWarnings("unchecked") final List<T> result = crit.list();

    return result;
  }

  /**
   * @see GenericDAO#findByCriteria(Criterion...)
   */
  @Override
  public List<T> findByCriteria(final Criterion... criterion) {
    return findByCriteria(-1, -1, criterion);
  }

  /**
   * @see GenericDAO#findByCriteria(int, int, Criterion...)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findByCriteria(final int firstResult,
                                final int maxResults, final Criterion... criterion) {
    Session session = (Session) em.getDelegate();
    Criteria crit = session.createCriteria(getEntityClass());

    for (final Criterion c : criterion) {
      crit.add(c);
    }

    if (firstResult > 0) {
      crit.setFirstResult(firstResult);
    }

    if (maxResults > 0) {
      crit.setMaxResults(maxResults);
    }

    final List<T> result = crit.list();
    return result;
  }

  /**
   * @see GenericDAO#findByCriteria(int, int, Map, Criterion...)
   */
  @SuppressWarnings("unchecked")
  @Override
  public List<T> findByCriteria(final int firstResult,
                                final int maxResults, final Map<String, String> aliases, final Criterion... criterion) {
    Session session = (Session) em.getDelegate();
    Criteria crit = session.createCriteria(getEntityClass());

    for (Map.Entry<String, String> alias : aliases.entrySet()) {
      crit.createAlias(alias.getKey(), alias.getValue());
    }

    for (final Criterion c : criterion) {
      crit.add(c);
    }

    if (firstResult > 0) {
      crit.setFirstResult(firstResult);
    }

    if (maxResults > 0) {
      crit.setMaxResults(maxResults);
    }

    final List<T> result = crit.list();
    return result;
  }

  /**
   * @see GenericDAO#countByCriteria(Criterion...)
   */
  @Override
  public int countByCriteria(Criterion... criterion) {
    Session session = (Session) em.getDelegate();
    Criteria crit = session.createCriteria(getEntityClass());
    crit.setProjection(Projections.rowCount());

    for (final Criterion c : criterion) {
      crit.add(c);
    }

    return ((Long) crit.list().get(0)).intValue();
  }

  /**
   * @see GenericDAO#countByCriteria(Map, List)
   */
  @Override
  public int countByCriteria(Map<String, String> aliases, List<Criterion> criterion) {
    Session session = (Session) em.getDelegate();
    Criteria crit = session.createCriteria(getEntityClass());
    crit.setProjection(Projections.rowCount());

    for (final Map.Entry<String, String> alias : aliases.entrySet()) {
      crit.createAlias(alias.getKey(), alias.getValue());
    }

    for (final Criterion c : criterion) {
      crit.add(c);
    }

    return ((Long) crit.list().get(0)).intValue();
  }

  /**
   * @see GenericDAO#hbnSequence()
   * @return
   */
	/*public Long hbnSequence(){
		Session session = (Session) em.getDelegate();
        return ((BigInteger)
                session.createSQLQuery("select next_val as id_val from hibernate_sequence for update")
                        .list().get(0)).longValue();
	}*/

}
