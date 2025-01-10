package edu.umb.uaspbo.entity;

import java.util.Date;

public class Mechanic {
    private int id;
    private String name;
    private String phone;
    private String specialization;
    private Date createdAt;

    public Mechanic() {
    }

    public Mechanic(int id, String name, String phone, String specialization, Date createdAt) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.specialization = specialization;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
