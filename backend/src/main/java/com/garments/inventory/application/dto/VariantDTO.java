package com.garments.inventory.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class VariantDTO {
    private UUID id;
    private UUID productId;
    private String size;
    private String color;
    private String fabric;
    private int quantity;
    private String sku;
    private LocalDateTime createdAt;

    // Constructors
    public VariantDTO() {}

    public VariantDTO(UUID id, UUID productId, String size, String color, String fabric, int quantity, String sku, LocalDateTime createdAt) {
        this.id = id;
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.fabric = fabric;
        this.quantity = quantity;
        this.sku = sku;
        this.createdAt = createdAt;
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