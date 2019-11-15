package com.users.ejb;


import com.users.model.Person;

/**
 * The com.users.ejb PersonBean.java
 *
 * @author Daniel Mboya
 * @version 1.0.0
 * @since Apr 25, 2017 3:51:54 AM
 */
public interface PersonBean {

  /**
   * add person
   *
   * @param person {@link Person} to be added
   * @return {@link Person} the saved instance
   * @throws Exception
   */
  Person save(Person person) throws Exception;
  Person update(Person person) throws Exception;
  Person findById(Long id);
}
