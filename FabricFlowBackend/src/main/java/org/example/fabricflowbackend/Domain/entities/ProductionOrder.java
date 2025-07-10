package org.example.fabricflowbackend.Domain.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProductionOrder {
    private UUID id;
    private UUID variantId;
    private Integer quantity;
    private String status; // CREATED, IN_PROGRESS, COMPLETED
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;

    public ProductionOrder() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.status = "CREATED";
    }

    public ProductionOrder(UUID variantId, Integer quantity) {
        this();
        this.variantId = variantId;
        this.quantity = quantity;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getVariantId() { return variantId; }
    public void setVariantId(UUID variantId) { this.variantId = variantId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public void startProduction() {
        this.status = "IN_PROGRESS";
        this.startDate = LocalDate.now();
    }

    public void completeProduction() {
        this.status = "COMPLETED";
        this.endDate = LocalDate.now();
    }
}