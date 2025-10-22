package com.frankmoley.lil;

import java.util.List;
import java.util.Optional;

import com.frankmoley.lil.data.dao.ServiceDao;
import com.frankmoley.lil.data.entity.Service;

public class App {
    public static void main(String[] args) {
        ServiceDao serviceDao = new ServiceDao();
        List<Service> services = serviceDao.getAll();
        System.out.println("*** SERVICES ***");
        System.out.println("*** GET_ALL ***");
        services.forEach(System.out::println);

        Optional<Service> service = serviceDao.getOne(services.get(0).getServiceId());
        System.out.println("*** GET_ONE ***\n" + service.get());

        Service newService = new Service();
        newService.setName("New Service" + System.currentTimeMillis());
        newService.setPrice(java.math.BigDecimal.valueOf(123.45));
        Service createdService = serviceDao.create(newService);
        System.out.println("*** CREATE ***\n" + createdService);

        createdService.setName("Updated " + createdService.getName());
        Service updatedService = serviceDao.update(createdService);
        System.out.println("*** UPDATE ***\n" + updatedService);

        serviceDao.delete(updatedService.getServiceId());
    }
}
