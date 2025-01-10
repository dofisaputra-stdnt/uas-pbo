package edu.umb.uaspbo.dto;

import java.sql.Date;

public class OrderDetailsDTO {
    private int orderId;
    private Date orderDate;
    private String clientName;
    private String clientPhone;
    private String serviceName;
    private int quantity;
    private double price;
    private double subtotal;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(int orderId, Date orderDate, String clientName, String clientPhone, String serviceName, int quantity, double price, double subtotal) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.serviceName = serviceName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
