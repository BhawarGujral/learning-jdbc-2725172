package com.frankmoley.lil.data.dao;

import com.frankmoley.lil.data.entity.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ServiceDao implements Dao<Service, UUID> {

  @Override
  public List<Service> getAll() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getAll'");
  }

  @Override
  public Service create(Service entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public Optional<Service> getOne(UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getOne'");
  }

  @Override
  public Service update(Service entity) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  private List<Service> processResultSet(ResultSet rs) throws SQLException {
    List<Service> services = new ArrayList<>();
    while(rs.next()){
      Service service = new Service();
      service.setId((UUID)rs.getObject("service_id"));
      service.setName(rs.getString("name"));
      service.setPrice(rs.getBigDecimal("price"));
      services.add(service);
    }
    return services;
  }
}
