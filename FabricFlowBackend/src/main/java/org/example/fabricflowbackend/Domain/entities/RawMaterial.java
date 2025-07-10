package org.example.fabricflowbackend.Domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class RawMaterial {
    private UUID id;
    private String name;
    private String unit;
    private int currentStock;
    private int reorderLevel;
    private String category;
    private LocalDateTime createdAt;

    public RawMaterial() {
        this.createdAt = LocalDateTime.now();
    }

    public RawMaterial(String name, String unit, int currentStock, int reorderLevel, String category) {
        this();
        this.name = name;
        this.unit = unit;
        this.currentStock = currentStock;
        this.reorderLevel = reorderLevel;
        this.category = category;
    }

    // Business logic methods
    public void consumeStock(int amount) {
        if (amount > currentStock) {
            throw new IllegalArgumentException("Cannot consume more stock than available");
        }
        this.currentStock -= amount;
    }

    public void addStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Cannot add negative stock");
        }
        this.currentStock += amount;
    }

    public boolean needsReorder() {
        return currentStock <= reorderLevel;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public int getCurrentStock() { return currentStock; }
    public void setCurrentStock(int currentStock) { this.currentStock = currentStock; }

    public int getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(int reorderLevel) { this.reorderLevel = reorderLevel; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}