package org.example.fabricflowbackend.Domain.entities;

import java.math.BigDecimal;
import java.util.UUID;

public class SalesItem {
    private UUID id;
    private UUID salesOrderId;
    private UUID variantId;
    private Integer quantity;
    private BigDecimal unitPrice;

    public SalesItem() {
        this.id = UUID.randomUUID();
    }

    public SalesItem(UUID variantId, Integer quantity, BigDecimal unitPrice) {
        this();
        this.variantId = variantId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(UUID salesOrderId) { this.salesOrderId = salesOrderId; }

    public UUID getVariantId() { return variantId; }
    public void setVariantId(UUID variantId) { this.variantId = variantId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}