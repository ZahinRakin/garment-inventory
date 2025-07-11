package org.example.fabricflowbackend.application.dto.purchaseitem;


import java.math.BigDecimal;
import java.util.UUID;

public class PurchaseItemRequestDTO {
    private UUID materialId;
    private int quantity;
    private BigDecimal unitPrice;

    // Getters and Setters
    public UUID getMaterialId() {
        return materialId;
    }

    public void setMaterialId(UUID materialId) {
        this.materialId = materialId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}