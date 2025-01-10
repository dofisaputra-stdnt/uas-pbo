package edu.umb.uaspbo.repository.impl;

import edu.umb.uaspbo.base.BaseRepository;
import edu.umb.uaspbo.dto.OrderDTO;
import edu.umb.uaspbo.entity.Order;
import edu.umb.uaspbo.repository.OrderRepository;
import edu.umb.uaspbo.util.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl extends BaseRepository<Order> implements OrderRepository {
    public OrderRepositoryImpl(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "orders";
    }

    @Override
    protected Order mapToEntity(ResultSet resultSet) {
        return EntityMapper.mapToEntity(resultSet, Order.class);
    }

    @Override
    public void save(Order order) {
        String query = "INSERT INTO " + getTableName() + " (client_id, order_date, event_date, total_amount, status) VALUES (?, ?, ?, ?, ?)";
        try (var statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getClientId());
            statement.setDate(2, Date.valueOf(order.getOrderDate()));
            statement.setDate(3, Date.valueOf(order.getEventDate()));
            statement.setDouble(4, order.getTotalAmount());
            statement.setString(5, order.getStatus().name());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void update(Order order) {
        String query = "UPDATE " + getTableName() + " SET client_id = ?, order_date = ?, event_date = ?, total_amount = ?, status = ? WHERE id = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setLong(1, order.getClientId());
            statement.setDate(2, Date.valueOf(order.getOrderDate()));
            statement.setDate(3, Date.valueOf(order.getEventDate()));
            statement.setDouble(4, order.getTotalAmount());
            statement.setString(5, order.getStatus().name());
            statement.setLong(6, order.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public List<OrderDTO> findAllOrders() {
        String query = "SELECT o.*, c.name AS client FROM orders o JOIN clients c ON o.client_id = c.id;";
        List<OrderDTO> entities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entities.add(EntityMapper.mapToEntity(resultSet, OrderDTO.class));
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
        return entities;
    }
}
