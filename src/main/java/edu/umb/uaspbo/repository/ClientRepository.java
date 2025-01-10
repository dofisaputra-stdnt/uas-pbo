package edu.umb.uaspbo.repository;

import edu.umb.uaspbo.entity.Client;

import java.util.List;

public interface ClientRepository {
    Client findById(int id);

    Client findByName(String name);

    List<Client> findAll();

    void save(Client client);

    void update(Client client);

    void deleteById(int id);
}
