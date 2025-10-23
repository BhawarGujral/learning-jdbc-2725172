package com.frankmoley.lil.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import com.frankmoley.lil.data.entity.Customer;
import com.frankmoley.lil.data.util.DatabaseUtils;

public class CustomerDao implements Dao<Customer, UUID> {
  private static final Logger LOGGER = Logger.getLogger(CustomerDao.class.getName());

  private static final String GET_ALL = "select customer_id, first_name, last_name, email, phone, address from wisdom.customers";
  private static final String GET_ONE = "select customer_id, first_name, last_name, email, phone, address from wisdom.customers where customer_id = ?";
  private static final String CREATE = "insert into wisdom.customers (customer_id, first_name, last_name, email, phone, address) values (?, ?, ?, ?, ?, ?)";
  private static final String UPDATE = "update wisdom.customers set first_name = ?, last_name = ?, email = ?, phone = ?, address = ? where customer_id = ?";
  private static final String DELETE = "delete from wisdom.customers where customer_id = ?";

  @Override
  public List<Customer> getAll() {
    List<Customer> customers = new ArrayList<>();
    Connection connection = DatabaseUtils.getConnection();
    try (Statement stmt = connection.createStatement()) {
      ResultSet rs = stmt.executeQuery(GET_ALL);
      customers = this.processResultSet(rs);
    } catch (SQLException e) {
      DatabaseUtils.handleSqlException("ServiceDao.getAll", e, LOGGER);
    }
    return customers;
  }

  @Override
  public Customer create(Customer entity) {
    UUID customerID = UUID.randomUUID();
    Connection connection = DatabaseUtils.getConnection();
    try {
      connection.setAutoCommit(false);
      PreparedStatement stmt = connection.prepareStatement(CREATE);
      stmt.setObject(1, customerID);
      stmt.setString(2, entity.getFirst_name());
      stmt.setString(3, entity.getLast_name());
      stmt.setString(4, entity.getEmail());
      stmt.setString(5, entity.getPhone());
      stmt.setString(6, entity.getAddress());
      stmt.execute();
      connection.commit();
      stmt.close();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException ex) {
        DatabaseUtils.handleSqlException("CustomerDao.create.rollback", ex, LOGGER);
      }
      DatabaseUtils.handleSqlException("CustomerDao.create", e, LOGGER);
    }
    return this.getOne(customerID).orElse(null);
  }

  @Override
  public Optional<Customer> getOne(UUID id) {
    Connection connection = DatabaseUtils.getConnection();
    try (PreparedStatement stmt = connection.prepareStatement(GET_ONE);) {
      stmt.setObject(1, id);
      ResultSet rs = stmt.executeQuery();
      List<Customer> customers = this.processResultSet(rs);
      if (customers.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(customers.get(0));
    } catch (SQLException e) {
      DatabaseUtils.handleSqlException("CustomerDao.getOne", e, LOGGER);
    }
    return Optional.empty();
  }

  @Override
  public Customer update(Customer entity) {
    Connection connection = DatabaseUtils.getConnection();
    try {
      connection.setAutoCommit(false);
      PreparedStatement stmt = connection.prepareStatement(UPDATE);
      stmt.setString(1, entity.getFirst_name());
      stmt.setString(2, entity.getLast_name());
      stmt.setString(3, entity.getEmail());
      stmt.setString(4, entity.getPhone());
      stmt.setString(5, entity.getAddress());
      stmt.setObject(6, entity.getCustomer_id());
      stmt.executeUpdate();
      connection.commit();
      stmt.close();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException ex) {
        DatabaseUtils.handleSqlException("CustomerDao.update.rollback", ex, LOGGER);
      }
      DatabaseUtils.handleSqlException("CustomerDao.update", e, LOGGER);
    }
    return this.getOne(entity.getCustomer_id()).orElse(null);
  }

  @Override
  public void delete(UUID id) {
    Connection connection = DatabaseUtils.getConnection();
    try {
      connection.setAutoCommit(false);
      PreparedStatement statement = connection.prepareStatement(DELETE);
      statement.setObject(1, id);
      statement.executeUpdate();
      connection.commit();
      statement.close();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException sqle) {
        DatabaseUtils.handleSqlException("CustomerDao.delete.rollback", sqle, LOGGER);
      }
      DatabaseUtils.handleSqlException("CustomerDao.delete", e, LOGGER);
    }
  }

  private List<Customer> processResultSet(ResultSet rs) throws SQLException {
    List<Customer> customers = new ArrayList<>();
    while (rs.next()) {
      Customer customer = new Customer();
      customer.setCustomer_id((UUID) rs.getObject("customer_id"));
      customer.setFirst_name(rs.getString("first_name"));
      customer.setLast_name(rs.getString("last_name"));
      customer.setEmail(rs.getString("email"));
      customer.setPhone(rs.getString("phone"));
      customer.setAddress(rs.getString("address"));
      customers.add(customer);
    }
    return customers;
  }
}
