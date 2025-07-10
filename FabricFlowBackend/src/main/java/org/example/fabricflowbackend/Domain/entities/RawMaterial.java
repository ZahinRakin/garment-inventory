package org.example.fabricflowbackend.Domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class RawMaterial {
    private UUID id;
    private String name;
    private String unit;
    private Integer currentStock;
    private Integer reorderLevel;
    private String category;
    private LocalDateTime createdAt;

    public RawMaterial() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.currentStock = 0;
        this.reorderLevel = 0;
    }

    public RawMaterial(String name, String unit, String category, Integer reorderLevel) {
        this();
        this.name = name;
        this.unit = unit;
        this.category = category;
        this.reorderLevel = reorderLevel;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Integer getCurrentStock() { return currentStock; }
    public void setCurrentStock(Integer currentStock) { this.currentStock = currentStock; }

    public Integer getReorderLevel() { return reorderLevel; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public void addStock(int quantity) {
        this.currentStock += quantity;
    }

    public boolean consumeStock(int quantity) {
        if (this.currentStock >= quantity) {
            this.currentStock -= quantity;
            return true;
        }
        return false;
    }

    public boolean isReorderNeeded() {
        return this.currentStock <= this.reorderLevel;
    }
}