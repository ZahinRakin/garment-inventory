// domain/entities/User.java
package com.garments.inventory.domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String role; // ADMIN, STORE_MANAGER, PRODUCTION_OFFICER, SALES_OFFICER
    private LocalDateTime createdAt;

    public User() {}

    public User(String username, String password, String email, String role) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}