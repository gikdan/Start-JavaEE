package com.generic.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@MappedSuperclass
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)

public abstract class ModelBaseImpl implements Serializable {

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  @Column(name = "id")
  protected Long id;



  /**
   * //_id
   *
   * @return the id
   */
  public Long getId() {
    return id;
  }



  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ModelBaseImpl other = (ModelBaseImpl) obj;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return getClass().getCanonicalName() + "{" + "id=" + id + '}';
  }


}


