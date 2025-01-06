package edu.umb.uaspbo.dto;

public class OrderDetailsDTO {
    private Long orderId;
    private String orderDate;
    private String clientName;
    private String clientPhone;
    private String serviceName;
    private int quantity;
    private double price;
    private double subtotal;

    public OrderDetailsDTO() {
    }

    public OrderDetailsDTO(Long orderId, String orderDate, String clientName, String clientPhone, String serviceName, int quantity, double price, double subtotal) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.serviceName = serviceName;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
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
