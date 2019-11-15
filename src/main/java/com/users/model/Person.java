package com.users.model;

import com.generic.model.ModelBaseImpl;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by danielmboya on 3/1/17.
 */


@Entity
@Table(name = "person")

@NamedQueries({
  @NamedQuery(
    name = Person.FIND_BY_EMAIL,
    query = "FROM Person u WHERE u.email=:uEmail"
  ),
  @NamedQuery(
    name = Person.FIND_BY_PHONE,
    query = "FROM Person u WHERE u.mobileNo=:uPhone"
  )

})


public class Person extends ModelBaseImpl {
  public static final String FIND_BY_EMAIL = "Person.findByUserEmail";
  public static final String FIND_BY_PHONE = "Person.findByUserPhone";

  @Column(name = "surname")
  private String surname;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "dob")
  @Temporal(TemporalType.DATE)
  private Date dob;

  @Column(name = "email",unique = true)
  private String email;


  @Column(name = "mobile_no",unique = true)
  private String mobileNo;


  public Person() {
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobileNo() {
    return mobileNo;
  }

  public void setMobileNo(String mobileNo) {
    this.mobileNo = mobileNo;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Person)) {
      return false;
    }
    Person other = (Person) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }


}
