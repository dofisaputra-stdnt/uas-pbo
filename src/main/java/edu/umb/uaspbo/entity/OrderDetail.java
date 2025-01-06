package edu.umb.uaspbo.entity;

import java.math.BigDecimal;

public class OrderDetail {
    private Long orderDetailId;
    private Long orderId;
    private Long serviceId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subtotal;

    public OrderDetail() {
    }

    public OrderDetail(Long orderDetailId, Long orderId, Long serviceId, int quantity, BigDecimal price, BigDecimal subtotal) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.serviceId = serviceId;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
