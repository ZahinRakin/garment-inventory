package org.example.fabricflowbackend.Domain.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class PurchaseItem {
    private UUID id;
    private UUID purchaseId;
    private UUID materialId;
    private int quantity;
    private BigDecimal unitPrice;

    public PurchaseItem() {

    }

    public PurchaseItem(UUID purchaseId, UUID materialId, int quantity, BigDecimal unitPrice) {
        this();
        this.purchaseId = purchaseId;
        this.materialId = materialId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Business logic methods
    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getPurchaseId() { return purchaseId; }
    public void setPurchaseId(UUID purchaseId) { this.purchaseId = purchaseId; }

    public UUID getMaterialId() { return materialId; }
    public void setMaterialId(UUID materialId) { this.materialId = materialId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}
