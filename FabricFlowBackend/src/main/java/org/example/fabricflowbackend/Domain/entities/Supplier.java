package org.example.fabricflowbackend.Domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Supplier {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;

    public Supplier() {
        this.createdAt = LocalDateTime.now();
    }

    public Supplier(String name, String email, String phone, String address) {
        this();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
