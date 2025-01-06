package edu.umb.uaspbo.dao;

import edu.umb.uaspbo.entity.Client;
import edu.umb.uaspbo.util.DBUtil;
import edu.umb.uaspbo.util.DialogUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public List<String> getAllClientNames() {
        String query = "SELECT name FROM clients";
        List<String> clientNames = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                clientNames.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return clientNames;
    }

    public Client createClient(Client client) {
        String query = "INSERT INTO clients (name, phone, email, address, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, client.getName());
            statement.setString(2, client.getPhone());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getAddress());
            statement.setTimestamp(5, Timestamp.valueOf(client.getCreatedAt()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    client.setClientId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return client;
    }

    public Client getClient(Long clientId) {
        String query = "SELECT * FROM clients WHERE client_id = ?";
        Client client = null;
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, clientId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                client = new Client();
                client.setClientId(resultSet.getLong("client_id"));
                client.setName(resultSet.getString("name"));
                client.setPhone(resultSet.getString("phone"));
                client.setEmail(resultSet.getString("email"));
                client.setAddress(resultSet.getString("address"));
                client.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return client;
    }

    public List<Client> getAllClients() {
        String query = "SELECT * FROM clients";
        List<Client> clients = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Client client = new Client();
                client.setClientId(resultSet.getLong("client_id"));
                client.setName(resultSet.getString("name"));
                client.setPhone(resultSet.getString("phone"));
                client.setEmail(resultSet.getString("email"));
                client.setAddress(resultSet.getString("address"));
                client.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                clients.add(client);
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return clients;
    }

    public boolean updateClient(Client client) {
        String query = "UPDATE clients SET name = ?, phone = ?, email = ?, address = ?, created_at = ? WHERE client_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, client.getName());
            statement.setString(2, client.getPhone());
            statement.setString(3, client.getEmail());
            statement.setString(4, client.getAddress());
            statement.setTimestamp(5, Timestamp.valueOf(client.getCreatedAt()));
            statement.setLong(6, client.getClientId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }

    public boolean deleteClient(Long clientId) {
        String query = "DELETE FROM clients WHERE client_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, clientId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }
}

