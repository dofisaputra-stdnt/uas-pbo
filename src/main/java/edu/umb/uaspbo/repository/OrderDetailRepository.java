package edu.umb.uaspbo.repository;

import edu.umb.uaspbo.dto.OrderDetailsDTO;
import edu.umb.uaspbo.entity.OrderDetail;

import java.util.List;

public interface OrderDetailRepository {
    OrderDetail findById(int id);

    List<OrderDetail> findAll();

    void save(OrderDetail orderDetail);

    void update(OrderDetail orderDetail);

    void deleteById(int id);

    void deleteByOrderId(int orderId);

    List<OrderDetailsDTO> findAllOrderDetails();
}
