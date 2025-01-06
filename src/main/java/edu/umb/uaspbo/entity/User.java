package edu.umb.uaspbo.entity;

import java.time.LocalDateTime;

public class User {
    private Long userId;
    private String username;
    private String password;
    private Role role;
    private LocalDateTime createdAt;

    public enum Role {
        ADMIN,
        STAFF
    }

    public User() {
    }

    public User(Long userId, String username, String password, Role role, LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
