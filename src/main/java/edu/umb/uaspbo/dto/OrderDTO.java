package edu.umb.uaspbo.dto;

import java.sql.Date;

public class OrderDTO {
    private int id;
    private String client;
    private String mechanic;
    private Date orderDate;
    private Date eventDate;
    private double totalAmount;
    private String status;
    private java.util.Date createdAt;

    public OrderDTO() {
    }

    public OrderDTO(int id, String client, String mechanic, Date orderDate, Date eventDate, double totalAmount, String status, java.util.Date createdAt) {
        this.id = id;
        this.client = client;
        this.mechanic = mechanic;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getMechanic() {
        return mechanic;
    }

    public void setMechanic(String mechanic) {
        this.mechanic = mechanic;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public java.util.Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
