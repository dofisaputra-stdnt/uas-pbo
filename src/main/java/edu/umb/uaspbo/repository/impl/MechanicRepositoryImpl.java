package edu.umb.uaspbo.repository.impl;

import edu.umb.uaspbo.base.BaseRepository;
import edu.umb.uaspbo.entity.Mechanic;
import edu.umb.uaspbo.repository.MechanicRepository;
import edu.umb.uaspbo.util.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MechanicRepositoryImpl extends BaseRepository<Mechanic> implements MechanicRepository {
    public MechanicRepositoryImpl(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "mechanics";
    }

    @Override
    protected Mechanic mapToEntity(ResultSet resultSet) {
        return EntityMapper.mapToEntity(resultSet, Mechanic.class);
    }

    @Override
    public Mechanic findByName(String name) {
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
    public void save(Mechanic mechanic) {
        String query = "INSERT INTO " + getTableName() + " (name, phone, specialization) VALUES (?, ?, ?)";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, mechanic.getName());
            statement.setString(2, mechanic.getPhone());
            statement.setString(3, mechanic.getSpecialization());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void update(Mechanic mechanic) {
        String query = "UPDATE " + getTableName() + " SET name = ?, phone = ?, specialization = ? WHERE id = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setString(1, mechanic.getName());
            statement.setString(2, mechanic.getPhone());
            statement.setString(3, mechanic.getSpecialization());
            statement.setLong(4, mechanic.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }
}
