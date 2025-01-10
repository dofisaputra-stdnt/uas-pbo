package edu.umb.uaspbo.repository;

import edu.umb.uaspbo.entity.User;

import java.util.List;

public interface UserRepository {
    User findById(int id);

    User findByUsername(String username);

    List<User> findAll();

    void save(User user);

    void update(User user);

    void deleteById(int id);

    boolean login(String username, String password);
}
