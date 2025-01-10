package edu.umb.uaspbo.repository.impl;

import edu.umb.uaspbo.base.BaseRepository;
import edu.umb.uaspbo.entity.Client;
import edu.umb.uaspbo.repository.ClientRepository;
import edu.umb.uaspbo.util.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientRepositoryImpl extends BaseRepository<Client> implements ClientRepository {
    public ClientRepositoryImpl(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "clients";
    }

    @Override
    protected Client mapToEntity(ResultSet resultSet) {
        return EntityMapper.mapToEntity(resultSet, Client.class);
    }

    @Override
    public Client findByName(String name) {
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
    public void save(Client client) {
        String query = "INSERT INTO " + getTableName() + " (name, phone, email, address) VALUES (?, ?, ?, ?)";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getPhone());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getAddress());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void update(Client client) {
        String query = "UPDATE " + getTableName() + " SET name = ?, phone = ?, email = ?, address = ? WHERE id = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getPhone());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getAddress());
            statement.setLong(5, client.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }
}
