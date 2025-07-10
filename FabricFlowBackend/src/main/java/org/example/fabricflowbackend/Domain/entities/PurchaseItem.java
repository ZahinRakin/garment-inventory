package org.example.fabricflowbackend.Domain.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class PurchaseItem {
    private UUID id;
    private UUID purchaseId;
    private UUID materialId;
    private Integer quantity;
    private BigDecimal unitPrice;

    public PurchaseItem() {
        this.id = UUID.randomUUID();
    }

    public PurchaseItem(UUID materialId, Integer quantity, BigDecimal unitPrice) {
        this();
        this.materialId = materialId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getPurchaseId() { return purchaseId; }
    public void setPurchaseId(UUID purchaseId) { this.purchaseId = purchaseId; }

    public UUID getMaterialId() { return materialId; }
    public void setMaterialId(UUID materialId) { this.materialId = materialId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}