package edu.umb.uaspbo.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order {
    private Long orderId;
    private Long clientId;
    private LocalDate orderDate;
    private LocalDate eventDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        COMPLETED,
        CANCELED
    }

    public Order() {
    }

    public Order(Long orderId, Long clientId, LocalDate orderDate, LocalDate eventDate, BigDecimal totalAmount, OrderStatus status, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.eventDate = eventDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
