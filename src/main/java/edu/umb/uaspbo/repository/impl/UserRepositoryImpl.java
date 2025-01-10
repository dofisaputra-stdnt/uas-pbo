package edu.umb.uaspbo.repository.impl;

import edu.umb.uaspbo.base.BaseRepository;
import edu.umb.uaspbo.entity.User;
import edu.umb.uaspbo.repository.UserRepository;
import edu.umb.uaspbo.util.EntityMapper;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {
    public UserRepositoryImpl(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    protected User mapToEntity(ResultSet resultSet) {
        return EntityMapper.mapToEntity(resultSet, User.class);
    }

    @Override
    public User findByUsername(String username) {
        String query = "SELECT * FROM " + getTableName() + " WHERE username = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next() ? mapToEntity(resultSet) : null;
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return null;
    }

    @Override
    public void save(User user) {
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
        String query = "INSERT INTO " + getTableName() + " (username, password, role) VALUES (?, ?, ?)";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void update(User user) {
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
        String query = "UPDATE " + getTableName() + " SET username = ?, password = ?, role = ? WHERE id = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public boolean login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (var statement = connection.prepareStatement(query)) {
            String shaPassword = DigestUtils.sha256Hex(password);
            statement.setString(1, username);
            statement.setString(2, shaPassword);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return false;
    }
}
