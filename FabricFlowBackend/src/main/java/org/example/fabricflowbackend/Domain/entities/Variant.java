package org.example.fabricflowbackend.Domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Variant {
    private UUID id;
    private UUID productId;
    private String size;
    private String color;
    private String fabric;
    private int quantity;
    private String sku;
    private LocalDateTime createdAt;

    public Variant() {
        this.createdAt = LocalDateTime.now();
    }

    public Variant(UUID productId, String size, String color, String fabric, int quantity, String sku) {
        this();
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.fabric = fabric;
        this.quantity = quantity;
        this.sku = sku;
    }

    // Business logic methods
    public void reduceStock(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("Cannot reduce stock by more than available quantity");
        }
        this.quantity -= amount;
    }

    public void increaseStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot increase stock by negative amount");
        }
        this.quantity += amount;
    }

    public boolean isLowStock(int threshold) {
        return quantity <= threshold;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getFabric() { return fabric; }
    public void setFabric(String fabric) { this.fabric = fabric; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}