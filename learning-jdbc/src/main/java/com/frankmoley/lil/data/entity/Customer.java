package com.frankmoley.lil.data.entity;

import java.util.UUID;

// customer_id UUID PRIMARY KEY,
//                            first_name VARCHAR,
//                            last_name VARCHAR,
//                            email VARCHAR UNIQUE,
//                            phone VARCHAR,
//                            address VARCHAR
public class Customer {
  private UUID customer_id;
  private String first_name;
  private String last_name;
  private String email;
  private String phone;
  private String address;

  public UUID getCustomer_id() {
    return customer_id;
  }

  public void setCustomer_id(UUID customer_id) {
    this.customer_id = customer_id;
  }

  public String getFirst_name() {
    return first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String toString() {
    return "Customer{" +
        "customer_id=" + customer_id +
        ", first_name='" + first_name + '\'' +
        ", last_name='" + last_name + '\'' +
        ", email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        ", address='" + address + '\'' +
        '}';
  }
}
