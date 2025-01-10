package edu.umb.uaspbo.repository;

import edu.umb.uaspbo.entity.Mechanic;

import java.util.List;

public interface MechanicRepository {
    Mechanic findById(int id);

    List<Mechanic> findAll();

    void save(Mechanic mechanic);

    void update(Mechanic mechanic);

    void deleteById(int id);
}
