package com.garments.inventory.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class RawMaterialDTO {
    private UUID id;
    private String name;
    private String unit;
    private int currentStock;
    private int reorderLevel;
    private String category;
    private LocalDateTime createdAt;

    // Constructors
    public RawMaterialDTO() {}

    public RawMaterialDTO(UUID id, String name, String unit, int currentStock, int reorderLevel, String category, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.unit = unit;
        this.currentStock = currentStock;
        this.reorderLevel = reorderLevel;
        this.category = category;
        this.createdAt = createdAt;
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