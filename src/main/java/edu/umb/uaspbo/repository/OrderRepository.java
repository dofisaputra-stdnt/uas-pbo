package edu.umb.uaspbo.repository;

import edu.umb.uaspbo.dto.OrderDTO;
import edu.umb.uaspbo.entity.Order;

import java.util.List;

public interface OrderRepository {
    Order findById(int id);

    List<Order> findAll();

    void save(Order order);

    void update(Order order);

    void deleteById(int id);

    List<OrderDTO> findAllOrders();
}
