package edu.umb.uaspbo.entity;

import java.time.LocalDateTime;

public class Mechanic {
    private Long mechanicId;
    private String name;
    private String phone;
    private String specialization;
    private LocalDateTime createdAt;

    public Mechanic() {
    }

    public Mechanic(Long mechanicId, String name, String phone, String specialization, LocalDateTime createdAt) {
        this.mechanicId = mechanicId;
        this.name = name;
        this.phone = phone;
        this.specialization = specialization;
        this.createdAt = createdAt;
    }

    public Long getMechanicId() {
        return mechanicId;
    }

    public void setMechanicId(Long mechanicId) {
        this.mechanicId = mechanicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
