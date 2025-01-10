package edu.umb.uaspbo.entity;

import java.time.LocalDate;
import java.util.Date;

public class Order {
    private int id;
    private int clientId;
    private LocalDate orderDate;
    private LocalDate eventDate;
    private double totalAmount;
    private OrderStatus status;
    private Date createdAt;

    public enum OrderStatus {
        PENDING,
        CONFIRMED,
        COMPLETED,
        CANCELED
    }

    public Order() {
    }

    public Order(int id, int clientId, LocalDate orderDate, LocalDate eventDate, double totalAmount, OrderStatus status, Date createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.orderDate = orderDate;
        this.eventDate = eventDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
