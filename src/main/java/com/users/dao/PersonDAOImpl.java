package com.users.dao;


import com.generic.dao.GenericDAOImpl;
import com.users.model.Person;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The com.users.dao PersonDAOImpl.java
 *
 * @author Daniel Mboya
 * @version 1.0.0
 * @since Apr 25, 2017 3:51:32 AM
 */
public class PersonDAOImpl extends GenericDAOImpl<Person, Long>
  implements PersonDAO {

  @Override
  public List<Person> findByUserEmail(String email) {
    Map<String, Object> params = new HashMap<>();
    params.put("uEmail", email);

    return findByNamedQuery(Person.FIND_BY_EMAIL, params);
  }


  @Override
  public List<Person> findByUserPhone(String phone) {
    Map<String, Object> params = new HashMap<>();
    params.put("uPhone", phone);

    return findByNamedQuery(Person.FIND_BY_PHONE, params);
  }
}
