package com.garments.inventory.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

public class ProductionOrder {
    private UUID id;
    private UUID variantId;
    private int quantity;
    private String status; // CREATED, IN_PROGRESS, COMPLETED
    private LocalDate startDate;
    private LocalDate endDate;

    public ProductionOrder() {}

    public ProductionOrder(UUID variantId, int quantity, String status, LocalDate startDate) {
        this.id = UUID.randomUUID();
        this.variantId = variantId;
        this.quantity = quantity;
        this.status = status;
        this.startDate = startDate;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getVariantId() { return variantId; }
    public void setVariantId(UUID variantId) { this.variantId = variantId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}