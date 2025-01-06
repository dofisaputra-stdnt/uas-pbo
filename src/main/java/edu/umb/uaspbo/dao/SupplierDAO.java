package edu.umb.uaspbo.dao;

import edu.umb.uaspbo.entity.Supplier;
import edu.umb.uaspbo.util.DBUtil;
import edu.umb.uaspbo.util.DialogUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

    public Supplier createSupplier(Supplier supplier) {
        String query = "INSERT INTO suppliers (name, phone, email, address, created_at) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getPhone());
            statement.setString(3, supplier.getEmail());
            statement.setString(4, supplier.getAddress());
            statement.setTimestamp(5, Timestamp.valueOf(supplier.getCreatedAt()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    supplier.setSupplierId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return supplier;
    }

    public Supplier getSupplier(Long supplierId) {
        String query = "SELECT * FROM suppliers WHERE supplier_id = ?";
        Supplier supplier = null;
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, supplierId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                supplier = new Supplier();
                supplier.setSupplierId(resultSet.getLong("supplier_id"));
                supplier.setName(resultSet.getString("name"));
                supplier.setPhone(resultSet.getString("phone"));
                supplier.setEmail(resultSet.getString("email"));
                supplier.setAddress(resultSet.getString("address"));
                supplier.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return supplier;
    }

    public List<Supplier> getAllSuppliers() {
        String query = "SELECT * FROM suppliers";
        List<Supplier> suppliers = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(resultSet.getLong("supplier_id"));
                supplier.setName(resultSet.getString("name"));
                supplier.setPhone(resultSet.getString("phone"));
                supplier.setEmail(resultSet.getString("email"));
                supplier.setAddress(resultSet.getString("address"));
                supplier.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return suppliers;
    }

    public boolean updateSupplier(Supplier supplier) {
        String query = "UPDATE suppliers SET name = ?, phone = ?, email = ?, address = ?, created_at = ? WHERE supplier_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getPhone());
            statement.setString(3, supplier.getEmail());
            statement.setString(4, supplier.getAddress());
            statement.setTimestamp(5, Timestamp.valueOf(supplier.getCreatedAt()));
            statement.setLong(6, supplier.getSupplierId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }

    public boolean deleteSupplier(Long supplierId) {
        String query = "DELETE FROM suppliers WHERE supplier_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, supplierId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }
}

