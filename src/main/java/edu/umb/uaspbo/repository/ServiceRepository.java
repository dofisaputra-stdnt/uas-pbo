package edu.umb.uaspbo.repository;

import edu.umb.uaspbo.entity.Service;

import java.util.List;

public interface ServiceRepository {
    Service findById(int id);

    Service findByName(String name);

    List<Service> findAll();

    void save(Service service);

    void update(Service service);

    void deleteById(int id);
}
