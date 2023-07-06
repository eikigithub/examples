package org.example.domain;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import io.ebean.Model;
import io.ebean.annotation.SoftDelete;

@Entity
public class Company extends Model {

  @Id
  UUID id;

  String name;
  
  @SoftDelete
  boolean deleted;
  
  @OneToOne(cascade = CascadeType.REFRESH, mappedBy = "company")
  Order order;
  
  public Company(String name) {
    this.name = name;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public boolean getDeleted() {
    return deleted;
  }
  
  public void setDeleted(boolean deleted) {
    this.deleted = deleted;
  }
  
  public Order getOrder() {  
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }
}