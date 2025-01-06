package edu.umb.uaspbo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OrderDTO {
    private Long orderId;
    private String client;
    private LocalDate orderDate;
    private LocalDate eventDate;
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime createdAt;

    public OrderDTO() {
    }

    public OrderDTO(Long orderId, String client, LocalDate orderDate, LocalDate eventDate, BigDecimal totalAmount, String status, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.client = client;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
