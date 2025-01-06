package edu.umb.uaspbo.dao;

import edu.umb.uaspbo.entity.OrderDetail;
import edu.umb.uaspbo.util.DBUtil;
import edu.umb.uaspbo.util.DialogUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {

    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        String query = "INSERT INTO order_details (order_id, service_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, orderDetail.getOrderId());
            statement.setLong(2, orderDetail.getServiceId());
            statement.setLong(3, orderDetail.getQuantity());
            statement.setBigDecimal(4, orderDetail.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    orderDetail.setOrderDetailId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return orderDetail;
    }

    public OrderDetail getOrderDetail(Long orderDetailId) {
        String query = "SELECT * FROM order_details WHERE order_detail_id = ?";
        OrderDetail orderDetail = null;
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderDetailId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                orderDetail = new OrderDetail();
                orderDetail.setOrderDetailId(resultSet.getLong("order_detail_id"));
                orderDetail.setOrderId(resultSet.getLong("order_id"));
                orderDetail.setServiceId(resultSet.getLong("service_id"));
                orderDetail.setQuantity(resultSet.getInt("quantity"));
                orderDetail.setPrice(resultSet.getBigDecimal("price"));
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return orderDetail;
    }

    public List<OrderDetail> getAllOrderDetails() {
        String query = "SELECT * FROM order_details";
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderDetailId(resultSet.getLong("order_detail_id"));
                orderDetail.setOrderId(resultSet.getLong("order_id"));
                orderDetail.setServiceId(resultSet.getLong("service_id"));
                orderDetail.setQuantity(resultSet.getInt("quantity"));
                orderDetail.setPrice(resultSet.getBigDecimal("price"));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return orderDetails;
    }

    public boolean updateOrderDetail(OrderDetail orderDetail) {
        String query = "UPDATE order_details SET order_id = ?, service_id = ?, quantity = ?, price = ?, created_at = ? WHERE order_detail_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderDetail.getOrderId());
            statement.setLong(2, orderDetail.getServiceId());
            statement.setLong(4, orderDetail.getQuantity());
            statement.setBigDecimal(5, orderDetail.getPrice());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }

    public boolean deleteOrderDetail(Long orderDetailId) {
        String query = "DELETE FROM order_details WHERE order_detail_id = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderDetailId);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            DialogUtil.showError(e.getMessage());
        }
        return false;
    }
}

