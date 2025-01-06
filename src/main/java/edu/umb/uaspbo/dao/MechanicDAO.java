package edu.umb.uaspbo.dao;

import edu.umb.uaspbo.entity.Mechanic;
import edu.umb.uaspbo.util.DBUtil;
import edu.umb.uaspbo.util.DialogUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MechanicDAO {

    public Mechanic createMechanic(Mechanic mechanic) {
        String query = "INSERT INTO mechanics (name, phone, specialization, created_at) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, mechanic.getName());
            statement.setString(2, mechanic.getPhone());
            statement.setString(3, mechanic.getSpecialization());
            statement.setTimestamp(4, Timestamp.valueOf(mechanic.getCreatedAt()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    mechanic.setMechanicId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return mechanic;
    }

    public Mechanic getMechanic(Long mechanicId) {
        String query = "SELECT * FROM mechanics WHERE mechanic_id = ?";
        Mechanic mechanic = null;
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, mechanicId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                mechanic = new Mechanic();
                mechanic.setMechanicId(resultSet.getLong("mechanic_id"));
                mechanic.setName(resultSet.getString("name"));
                mechanic.setPhone(resultSet.getString("phone"));
                mechanic.setSpecialization(resultSet.getString("specialization"));
                mechanic.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return mechanic;
    }

    public List<Mechanic> getAllMechanics() {
        String query = "SELECT * FROM mechanics";
        List<Mechanic> mechanics = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Mechanic mechanic = new Mechanic();
                mechanic.setMechanicId(resultSet.getLong("mechanic_id"));
                mechanic.setName(resultSet.getString("name"));
                mechanic.setPhone(resultSet.getString("phone"));
                mechanic.setSpecialization(resultSet.getString("specialization"));
                mechanic.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                mechanics.add(mechanic);
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return mechanics;
    }

    public boolean updateMechanic(Mechanic mechanic) {
        String query = "UPDATE mechanics SET name = ?, phone = ?, specialization = ?, created_at = ? WHERE mechanic_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, mechanic.getName());
            statement.setString(2, mechanic.getPhone());
            statement.setString(3, mechanic.getSpecialization());
            statement.setTimestamp(4, Timestamp.valueOf(mechanic.getCreatedAt()));
            statement.setLong(5, mechanic.getMechanicId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }

    public boolean deleteMechanic(Long mechanicId) {
        String query = "DELETE FROM mechanics WHERE mechanic_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, mechanicId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }
}

