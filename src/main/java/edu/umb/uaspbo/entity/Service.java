package edu.umb.uaspbo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Service {
    private Long serviceId;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime createdAt;

    public Service() {
    }

    public Service(Long serviceId, String name, String description, BigDecimal price, LocalDateTime createdAt) {
        this.serviceId = serviceId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
