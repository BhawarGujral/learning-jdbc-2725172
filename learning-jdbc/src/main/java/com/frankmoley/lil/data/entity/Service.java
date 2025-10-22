package com.frankmoley.lil.data.entity;

import java.util.UUID;
import java.math.BigDecimal;

public class Service {
  private UUID id;
  private String name;
  private BigDecimal price;

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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String toString() {
    return "Service{id=" + id + ", name='" + name + "', price=" + price + "}";
  }
}
