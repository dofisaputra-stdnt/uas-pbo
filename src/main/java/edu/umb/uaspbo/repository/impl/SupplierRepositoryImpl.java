package edu.umb.uaspbo.repository.impl;

import edu.umb.uaspbo.base.BaseRepository;
import edu.umb.uaspbo.entity.Supplier;
import edu.umb.uaspbo.repository.SupplierRepository;
import edu.umb.uaspbo.util.EntityMapper;

import java.sql.Connection;
import java.sql.ResultSet;

public class SupplierRepositoryImpl extends BaseRepository<Supplier> implements SupplierRepository {
    public SupplierRepositoryImpl(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "suppliers";
    }

    @Override
    protected Supplier mapToEntity(ResultSet resultSet) {
        return EntityMapper.mapToEntity(resultSet, Supplier.class);
    }

    @Override
    public void save(Supplier supplier) {
        String query = "INSERT INTO " + getTableName() + " (name, phone, email, address) VALUES (?, ?, ?, ?)";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getPhone());
            statement.setString(3, supplier.getEmail());
            statement.setString(4, supplier.getAddress());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void update(Supplier supplier) {
        String query = "UPDATE " + getTableName() + " SET name = ?, phone = ?, email = ?, address = ? WHERE id = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getPhone());
            statement.setString(3, supplier.getEmail());
            statement.setString(4, supplier.getAddress());
            statement.setLong(5, supplier.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }
}
