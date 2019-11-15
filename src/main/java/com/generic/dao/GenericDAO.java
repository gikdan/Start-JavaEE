package com.generic.dao;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Generic DAO, providing basic CRUD operations
 *
 * @param <T>  the entity type
 * @param <ID> the primary key type
 * @author Daniel Mboya
 */
public interface GenericDAO<T, ID extends Serializable> {

  /**
   * Set entity manager
   *
   * @param em {@link EntityManager}
   */
  void setEm(EntityManager em);

  /**
   * Returns hibernate session
   *
   * @return
   */
  Session getSession();

  /**
   * get entity manager
   *
   * @return {@link EntityManager}
   */
  EntityManager getEm();

  /**
   * Get the AppClass of the entity.
   *
   * @return the class
   */
  Class<T> getEntityClass();


  List<T> findByProperty(final int firstResult, final int maxResults, final String name, final Object value);
  /**
   * Find an entity by its primary key
   *
   * @param id the primary key
   * @return the entity
   */
  T findById(final ID id);

  /**
   * Load all entities.
   *
   * @return the list of entities
   */
  List<T> findAll();

  /**
   * Find entities based on an example.
   *
   * @param exampleInstance the example
   * @return the list of entities
   */
  List<T> findByExample(final T exampleInstance);

  /**
   * Find using a named query.
   *
   * @param queryName the name of the query
   * @param params    the query parameters
   * @return the list of entities
   */
  List<T> findByNamedQuery(final String queryName, Object... params);

  /**
   * Find using a named query.
   *
   * @param queryName the name of the query
   * @param params    the query parameters
   * @return the list of entities
   */
  List<T> findByNamedQuery(final String queryName, final Map<String, ? extends Object> params);

  /**
   * Count all entities.
   *
   * @return the number of entities
   */
  int countAll();

  /**
   * Count entities based on an example.
   *
   * @param exampleInstance the search criteria
   * @return the number of entities
   */
  int countByExample(final T exampleInstance);


  /**
   * save an entity. This can be either a INSERT or UPDATE in the database.
   *
   * @param entity the entity to save
   * @return the saved entity
   */
  T save(final T entity);

  /**
   * delete an entity from the database.
   *
   * @param entity the entity to delete
   */
  void delete(final T entity);

  /**
   * delete an entity by its primary key
   *
   * @param id the primary key of the entity to delete
   */
  void deleteById(final ID id);

  /**
   * delete batch entities by their primary keys array
   *
   * @param ids [] the primary key of entities to delete
   */
  void delete(ID ids[]) ;

  /**
   * @param firstResult    {@link Integer}
   * @param maxResults     {@link Integer}
   * @param projectionList {@link ProjectionList}
   * @param criterion      {@link List} of {@link Criterion}
   * @param orderBy        {@link List} of {@link String} default order is asc
   * @return
   */
  List<T> findByCriteria(final int firstResult,
                         final int maxResults, ProjectionList projectionList, final List<Criterion> criterion, final List<String> orderBy);

  /**
   * @param firstResult    {@link Integer}
   * @param maxResults     {@link Integer}
   * @param projectionList {@link ProjectionList}
   * @param criterion      {@link List} of {@link Criterion}
   * @param orderBy        {@link Map} of {@link String} key being order property and value being order by [desc,asc]
   * @return
   */
  List<T> findByCriteria(final int firstResult,
                         final int maxResults, ProjectionList projectionList, final List<Criterion> criterion, final Map<String, String> orderBy);

  /**
   * @param criterion varargs of {@link Criterion}
   * @return
   */
  List<T> findByCriteria(Criterion... criterion);

  /**
   * @param firstResult
   * @param maxResults
   * @param criterion
   * @return
   */
  List<T> findByCriteria(int firstResult, int maxResults,
                         Criterion... criterion);

  /**
   * @param firstResult
   * @param maxResults
   * @param aliases
   * @param criterion
   * @return
   */
  List<T> findByCriteria(int firstResult, int maxResults,
                         Map<String, String> aliases, Criterion... criterion);

  /**
   * Count by criteria
   *
   * @param criterion varargs of {@link Criterion}
   * @return
   */
  int countByCriteria(Criterion... criterion);

  /**
   * @param aliases
   * @param criterion
   * @return
   */
  int countByCriteria(Map<String, String> aliases, List<Criterion> criterion);

  /**
   * @param firstResult
   * @param maxResults
   * @param projectionList
   * @param aliases
   * @param criterion
   * @param orderBy
   * @return
   */
  List<T> findByCriteria(int firstResult, int maxResults,
                         ProjectionList projectionList, Map<String, String> aliases,
                         List<Criterion> criterion, List<String> orderBy);

  /**
   *
   * @return
   */
  //Long hbnSequence();

}
