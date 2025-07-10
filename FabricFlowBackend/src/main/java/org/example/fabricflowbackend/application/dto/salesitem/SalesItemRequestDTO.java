package org.example.fabricflowbackend.application.dto.salesitem;


import java.math.BigDecimal;
import java.util.UUID;

public class SalesItemRequestDTO {
    private UUID variantId;
    private int quantity;
    private BigDecimal unitPrice;

    // Getters and Setters
    public UUID getVariantId() {
        return variantId;
    }

    public void setVariantId(UUID variantId) {
        this.variantId = variantId;
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