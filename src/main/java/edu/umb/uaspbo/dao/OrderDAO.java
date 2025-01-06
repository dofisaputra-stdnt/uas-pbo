package edu.umb.uaspbo.dao;

import edu.umb.uaspbo.dto.OrderDTO;
import edu.umb.uaspbo.dto.OrderDetailsDTO;
import edu.umb.uaspbo.entity.Order;
import edu.umb.uaspbo.util.DBUtil;
import edu.umb.uaspbo.util.DialogUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<OrderDetailsDTO> getOrderDetails() {
        String query = """
                SELECT o.order_id,
                       o.order_date,
                       c.name  AS client_name,
                       c.phone AS client_phone,
                       s.name  AS service_name,
                       od.quantity,
                       od.price,
                       od.subtotal
                FROM order_details od
                         JOIN orders o ON od.order_id = o.order_id
                         JOIN clients c ON o.client_id = c.client_id
                         JOIN services s ON od.service_id = s.service_id
                ORDER BY o.order_id, s.name;
                """;

        List<OrderDetailsDTO> orderDetails = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                OrderDetailsDTO orderDetail = new OrderDetailsDTO();
                orderDetail.setOrderId(resultSet.getLong("order_id"));
                orderDetail.setOrderDate(resultSet.getDate("order_date").toString());
                orderDetail.setClientName(resultSet.getString("client_name"));
                orderDetail.setClientPhone(resultSet.getString("client_phone"));
                orderDetail.setServiceName(resultSet.getString("service_name"));
                orderDetail.setQuantity(resultSet.getInt("quantity"));
                orderDetail.setPrice(resultSet.getDouble("price"));
                orderDetail.setSubtotal(resultSet.getDouble("subtotal"));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return orderDetails;
    }

    public List<OrderDTO> getOrders() {
        String query = """
                select o.*, c.name as client
                from orders o
                         join clients c on o.client_id = c.client_id;
                """;

        List<OrderDTO> orders = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                OrderDTO order = new OrderDTO();
                order.setOrderId(resultSet.getLong("order_id"));
                order.setOrderDate(resultSet.getDate("order_date").toLocalDate());
                order.setEventDate(resultSet.getDate("event_date").toLocalDate());
                order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
                order.setStatus(resultSet.getString("status"));
                order.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                order.setClient(resultSet.getString("client"));
                orders.add(order);
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return orders;
    }

    public int getLastOrderId() {
        String query = "SELECT MAX(order_id) FROM orders";
        try (Connection connection = DBUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return 0;
    }

    public Order createOrder(Order order) {
        String query = "INSERT INTO orders (client_id, order_date, event_date, total_amount, status, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getClientId());
            statement.setDate(2, Date.valueOf(order.getOrderDate()));
            statement.setDate(3, Date.valueOf(order.getEventDate()));
            statement.setBigDecimal(4, order.getTotalAmount());
            statement.setString(5, order.getStatus().name());
            statement.setTimestamp(6, Timestamp.valueOf(order.getCreatedAt()));

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    order.setOrderId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return order;
    }

    public Order getOrder(Long orderId) {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        Order order = null;
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                order = new Order();
                order.setOrderId(resultSet.getLong("order_id"));
                order.setClientId(resultSet.getLong("client_id"));
                order.setOrderDate(resultSet.getDate("order_date").toLocalDate());
                order.setEventDate(resultSet.getDate("event_date").toLocalDate());
                order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
                order.setStatus(Order.OrderStatus.valueOf(resultSet.getString("status")));
                order.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return order;
    }

    public List<Order> getAllOrders() {
        String query = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong("order_id"));
                order.setClientId(resultSet.getLong("client_id"));
                order.setOrderDate(resultSet.getDate("order_date").toLocalDate());
                order.setEventDate(resultSet.getDate("event_date").toLocalDate());
                order.setTotalAmount(resultSet.getBigDecimal("total_amount"));
                order.setStatus(Order.OrderStatus.valueOf(resultSet.getString("status")));
                order.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                orders.add(order);
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return orders;
    }

    public boolean updateOrder(Order order) {
        String query = "UPDATE orders SET client_id = ?, order_date = ?, event_date = ?, total_amount = ?, status = ?, created_at = ? WHERE order_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, order.getClientId());
            statement.setDate(2, Date.valueOf(order.getOrderDate()));
            statement.setDate(3, Date.valueOf(order.getEventDate()));
            statement.setBigDecimal(4, order.getTotalAmount());
            statement.setString(5, order.getStatus().name());
            statement.setTimestamp(6, Timestamp.valueOf(order.getCreatedAt()));
            statement.setLong(7, order.getOrderId());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }

    public boolean deleteOrder(Long orderId) {
        String query = "DELETE FROM orders WHERE order_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }
}

