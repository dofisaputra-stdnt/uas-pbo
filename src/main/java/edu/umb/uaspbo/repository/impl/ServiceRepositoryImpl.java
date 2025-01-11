package edu.umb.uaspbo.repository.impl;

import edu.umb.uaspbo.base.BaseRepository;
import edu.umb.uaspbo.entity.Service;
import edu.umb.uaspbo.repository.ServiceRepository;
import edu.umb.uaspbo.util.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceRepositoryImpl extends BaseRepository<Service> implements ServiceRepository {
    public ServiceRepositoryImpl(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "services";
    }

    @Override
    protected Service mapToEntity(ResultSet resultSet) {
        return EntityMapper.mapToEntity(resultSet, Service.class);
    }

    @Override
    public Service findByName(String name) {
        String query = "SELECT * FROM " + getTableName() + " WHERE name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapToEntity(resultSet);
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return null;
    }

    @Override
    public void save(Service service) {
        String query = "INSERT INTO " + getTableName() + " (name, description, price) VALUES (?, ?, ?)";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, service.getName());
            statement.setString(2, service.getDescription());
            statement.setDouble(3, service.getPrice());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void update(Service service) {
        String query = "UPDATE " + getTableName() + " SET name = ?, description = ?, price = ? WHERE id = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, service.getName());
            statement.setString(2, service.getDescription());
            statement.setDouble(3, service.getPrice());
            statement.setLong(4, service.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }
}
