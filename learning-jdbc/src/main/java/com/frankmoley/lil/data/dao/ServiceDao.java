package com.frankmoley.lil.data.dao;

import com.frankmoley.lil.data.entity.Service;
import com.frankmoley.lil.data.util.DatabaseUtils;

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

public class ServiceDao implements Dao<Service, UUID> {
  private static final Logger LOGGER = Logger.getLogger(ServiceDao.class.getName());

  private static final String GET_ALL = "select service_id,name,price from wisdom.services";
  private static final String GET_BY_ID = "select service_id,name,price from wisdom.services where service_id = ?";
  private static final String CREATE = "insert into wisdom.services (service_id,name,price) values (?,?,?)";
  private static final String UPDATE = "update wisdom.services set name=?,price=? where service_id=?";
  private static final String DELETE = "delete from wisdom.services where service_id = ?";

  @Override
  public Service create(Service entity) {
    // Creating a Random UUID for the new Service being created
    UUID newServiceId = UUID.randomUUID();
    Connection connection = DatabaseUtils.getConnection();
    try {
      // To follow Atomic principle of transactions
      // if anything fails, we want to rollback
      connection.setAutoCommit(false);
      PreparedStatement stmt = connection.prepareStatement(CREATE);
      stmt.setObject(1, newServiceId);
      stmt.setString(2, entity.getName());
      stmt.setBigDecimal(3, entity.getPrice());
      stmt.execute();
      connection.commit();
      stmt.close();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException ex) {
        DatabaseUtils.handleSqlException("ServiceDao.create.rollback", ex, LOGGER);
      }
      DatabaseUtils.handleSqlException("ServiceDao.create", e, LOGGER);
    }
    return this.getOne(newServiceId).orElse(null);
  }

  @Override
  public void delete(UUID id) {
    Connection connection = DatabaseUtils.getConnection();
    try {
      connection.setAutoCommit(false);
      PreparedStatement stmt = connection.prepareStatement(DELETE);
      stmt.setObject(1, id);
      stmt.execute();
      connection.commit();
      stmt.close();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException ex) {
        DatabaseUtils.handleSqlException("ServiceDao.delete.rollback", ex, LOGGER);
      }
      DatabaseUtils.handleSqlException("ServiceDao.delete", e, LOGGER);
    }

  }

  @Override
  public List<Service> getAll() {
    List<Service> services = new ArrayList<>();
    Connection connection = DatabaseUtils.getConnection();
    try (Statement stmt = connection.createStatement()) {
      ResultSet rs = stmt.executeQuery(GET_ALL);
      services = this.processResultSet(rs);
    } catch (SQLException e) {
      DatabaseUtils.handleSqlException("ServiceDao.getAll", e, LOGGER);
    }
    return services;
  }

  @Override
  public Optional<Service> getOne(UUID id) {
    try (PreparedStatement stmt = DatabaseUtils.getConnection().prepareStatement(GET_BY_ID)) {
      stmt.setObject(1, id);
      ResultSet rs = stmt.executeQuery();
      List<Service> services = this.processResultSet(rs);
      if (services.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(services.get(0));
    } catch (SQLException e) {
      DatabaseUtils.handleSqlException("ServiceDao.getOne", e, LOGGER);
    }
    return Optional.empty();
  }

  @Override
  public Service update(Service entity) {
    Connection connection = DatabaseUtils.getConnection();
    try {
      connection.setAutoCommit(false);
      PreparedStatement stmt = connection.prepareStatement(UPDATE);
      stmt.setString(1, entity.getName());
      stmt.setBigDecimal(2, entity.getPrice());
      stmt.setObject(3, entity.getServiceId());
      stmt.execute();
      connection.commit();
      stmt.close();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException ex) {
        DatabaseUtils.handleSqlException("ServiceDao.update.rollback", ex, LOGGER);
      }
      DatabaseUtils.handleSqlException("ServiceDao.update", e, LOGGER);
    }
    return this.getOne(entity.getServiceId()).get();
  }

  private List<Service> processResultSet(ResultSet rs) throws SQLException {
    List<Service> services = new ArrayList<>();
    while (rs.next()) {
      Service service = new Service();
      service.setServiceId((UUID) rs.getObject("service_id"));
      service.setName(rs.getString("name"));
      service.setPrice(rs.getBigDecimal("price"));
      services.add(service);
    }
    return services;
  }
}
