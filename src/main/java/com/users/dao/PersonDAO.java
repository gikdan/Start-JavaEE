package com.users.dao;


import com.generic.dao.GenericDAO;
import com.users.model.Person;

import java.util.List;

/**
 * The com.users.dao PersonDAO.java
 *
 * @author Daniel Mboya
 * @version 1.0.0
 * @since Apr 25, 2017 3:51:39 AM
 */
public interface PersonDAO
  extends GenericDAO<Person, Long> {

  List<Person> findByUserEmail(String email);
  List<Person> findByUserPhone(String phone);
}
