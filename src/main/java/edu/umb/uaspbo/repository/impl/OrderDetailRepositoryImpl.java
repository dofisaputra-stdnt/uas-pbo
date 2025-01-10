package edu.umb.uaspbo.repository.impl;

import edu.umb.uaspbo.base.BaseRepository;
import edu.umb.uaspbo.dto.OrderDetailsDTO;
import edu.umb.uaspbo.entity.OrderDetail;
import edu.umb.uaspbo.repository.OrderDetailRepository;
import edu.umb.uaspbo.util.EntityMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailRepositoryImpl extends BaseRepository<OrderDetail> implements OrderDetailRepository {
    public OrderDetailRepositoryImpl(Connection connection) {
        super(connection);
    }

    @Override
    protected String getTableName() {
        return "order_details";
    }

    @Override
    protected OrderDetail mapToEntity(ResultSet resultSet) {
        return EntityMapper.mapToEntity(resultSet, OrderDetail.class);
    }

    @Override
    public void save(OrderDetail orderDetail) {
        String query = "INSERT INTO " + getTableName() + " (order_id, service_id, quantity, price, subtotal) VALUES (?, ?, ?, ?, ?)";
        try (var statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderDetail.getOrderId());
            statement.setLong(2, orderDetail.getServiceId());
            statement.setInt(3, orderDetail.getQuantity());
            statement.setDouble(4, orderDetail.getPrice());
            statement.setDouble(5, orderDetail.getSubtotal());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void update(OrderDetail orderDetail) {
        String query = "UPDATE " + getTableName() + " SET order_id = ?, service_id = ?, quantity = ?, price = ?, subtotal = ? WHERE id = ?";
        try (var statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderDetail.getOrderId());
            statement.setLong(2, orderDetail.getServiceId());
            statement.setInt(3, orderDetail.getQuantity());
            statement.setDouble(4, orderDetail.getPrice());
            statement.setDouble(5, orderDetail.getSubtotal());
            statement.setLong(6, orderDetail.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public List<OrderDetailsDTO> findAllOrderDetails() {
        String query = "SELECT o.id AS order_id, o.order_date AS order_date, c.name AS client_name, c.phone AS client_phone, s.name AS service_name, od.quantity, od.price, od.subtotal FROM order_details od JOIN orders o ON od.order_id = o.id JOIN clients c ON o.client_id = c.id JOIN services s ON od.service_id = s.id ORDER BY o.id, s.name;";
        List<OrderDetailsDTO> entities = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entities.add(EntityMapper.mapToEntity(resultSet, OrderDetailsDTO.class));
            }
        } catch (SQLException e) {
//            log.warning(e.getMessage());
            e.printStackTrace();
        }
        return entities;
    }
}
