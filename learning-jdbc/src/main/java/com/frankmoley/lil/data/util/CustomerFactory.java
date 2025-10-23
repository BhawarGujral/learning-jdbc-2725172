package com.frankmoley.lil.data.util;

import java.sql.SQLException;
import java.util.function.Consumer;

import com.frankmoley.lil.data.entity.Customer;

public class CustomerFactory {
  public static Customer create(Consumer<Customer> initializer) {
    Customer customer = new Customer();
    initializer.accept(customer);
    return customer;
  }
}
