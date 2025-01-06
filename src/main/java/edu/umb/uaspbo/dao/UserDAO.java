package edu.umb.uaspbo.dao;

import edu.umb.uaspbo.entity.User;
import edu.umb.uaspbo.util.DBUtil;
import edu.umb.uaspbo.util.DialogUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);

            String shaPassword = DigestUtils.sha256Hex(password);
            statement.setString(1, username);
            statement.setString(2, shaPassword);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }

    public User createUser(User user) {
        String query = "INSERT INTO users (username, password, role, created_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name());
            statement.setTimestamp(4, Timestamp.valueOf(user.getCreatedAt()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return user;
    }

    public User getUser(Long userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        User user = null;
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getLong("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(User.Role.valueOf(resultSet.getString("role")));
                user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return user;
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        List<User> users = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getLong("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(User.Role.valueOf(resultSet.getString("role").toUpperCase()));
                user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                users.add(user);
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return users;
    }

    public boolean updateUser(User user) {
        String query = "UPDATE users SET username = ?, password = ?, role = ?, created_at = ? WHERE user_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name());
            statement.setTimestamp(4, Timestamp.valueOf(user.getCreatedAt()));
            statement.setLong(5, user.getUserId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }

    public boolean deleteUser(Long userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }
}

