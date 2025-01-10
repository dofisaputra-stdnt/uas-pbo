package edu.umb.uaspbo.base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public abstract class BaseRepository<T> {
    protected final Logger log = Logger.getLogger(getClass().getName());
    protected final Connection connection;

    public BaseRepository(Connection connection) {
        this.connection = connection;
    }

    protected abstract String getTableName();

    protected abstract T mapToEntity(ResultSet resultSet);

    public T findById(int id) {
        String query = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToEntity(resultSet);
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return null;
    }

    public List<T> findAll() {
        String query = "SELECT * FROM " + getTableName();
        List<T> entities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entities.add(mapToEntity(resultSet));
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return entities;
    }

    public void deleteById(int id) {
        String query = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }
}