package edu.umb.uaspbo.repository;

import edu.umb.uaspbo.entity.Supplier;

import java.util.List;

public interface SupplierRepository {
    Supplier findById(int id);

    List<Supplier> findAll();

    void save(Supplier supplier);

    void update(Supplier supplier);

    void deleteById(int id);
}
