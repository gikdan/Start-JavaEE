package com.users.ejb;

import com.generic.exception.IncompleteRestUserCreationData;
import com.generic.exception.UserAlreadyExistException;
import com.users.dao.PersonDAO;
import com.users.model.Person;
import org.apache.log4j.Logger;

import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * The com.users.ejb PersonBeanImpl.java
 *
 * @author Daniel Mboya
 * @version 1.0.0
 * @since Apr 25, 2017 3:51:47 AM
 */

@Stateless
@Remote
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PersonBeanImpl implements PersonBean {

  private Logger logger = Logger.getLogger(getClass());

  @PersistenceContext
  private EntityManager em;

  @Inject
  private PersonDAO personDao;


  /**
   * @see PersonBean#save(Person)
   */
  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public Person save(Person person) throws Exception {

    if(person == null || person.getEmail() == null || person.getFirstName() == null ||
      person.getSurname()==null)
      throw new IncompleteRestUserCreationData();

    if (emailExist(person.getEmail())) {
      throw new UserAlreadyExistException("There is a person with that email address: " + person.getEmail());
    }

    if (person.getMobileNo() !=null && phoneExist(person.getMobileNo())) {
      throw new UserAlreadyExistException("There is a person with that mobile number: " + person.getMobileNo());
    }

    person = personDao.save(person);

    return person;
  }

  /**
   * @see PersonBean#save(Person)
   */
  @Override
  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public Person update(Person myperson) throws Exception {

    Person person=personDao.findById(myperson.getId());
    if(person !=null){
      myperson=personDao.save(myperson);
      return myperson;

    }

    return null;
  }




  @Transactional
  @Override
  public Person findById(Long id) {
    Person person=personDao.findById(id);
    return person;

  }



  private boolean emailExist(final String email) {
    List<Person> u=personDao.findByUserEmail(email);
    if(u.isEmpty() || u.get(0) == null)
      return false;
    return true;
  }


  private boolean phoneExist(final String phone) {
    List<Person> u=personDao.findByUserPhone(phone);
    if(u.isEmpty() || u.get(0) == null)
      return false;
    return true;
  }

}
