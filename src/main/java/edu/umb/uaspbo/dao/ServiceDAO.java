package edu.umb.uaspbo.dao;

import edu.umb.uaspbo.entity.Service;
import edu.umb.uaspbo.util.DBUtil;
import edu.umb.uaspbo.util.DialogUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {

    public Service createService(Service service) {
        String query = "INSERT INTO services (name, description, price, created_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, service.getName());
            statement.setString(2, service.getDescription());
            statement.setBigDecimal(3, service.getPrice());
            statement.setTimestamp(4, Timestamp.valueOf(service.getCreatedAt()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    service.setServiceId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return service;
    }

    public Service getService(Long serviceId) {
        String query = "SELECT * FROM services WHERE service_id = ?";
        Service service = null;
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, serviceId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                service = new Service();
                service.setServiceId(resultSet.getLong("service_id"));
                service.setName(resultSet.getString("name"));
                service.setDescription(resultSet.getString("description"));
                service.setPrice(resultSet.getBigDecimal("price"));
                service.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return service;
    }

    public List<Service> getAllServices() {
        String query = "SELECT * FROM services";
        List<Service> services = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Service service = new Service();
                service.setServiceId(resultSet.getLong("service_id"));
                service.setName(resultSet.getString("name"));
                service.setDescription(resultSet.getString("description"));
                service.setPrice(resultSet.getBigDecimal("price"));
                service.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                services.add(service);
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return services;
    }

    public boolean updateService(Service service) {
        String query = "UPDATE services SET name = ?, description = ?, price = ?, created_at = ? WHERE service_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, service.getName());
            statement.setString(2, service.getDescription());
            statement.setBigDecimal(3, service.getPrice());
            statement.setTimestamp(4, Timestamp.valueOf(service.getCreatedAt()));
            statement.setLong(5, service.getServiceId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }

    public boolean deleteService(Long serviceId) {
        String query = "DELETE FROM services WHERE service_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, serviceId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }
}

