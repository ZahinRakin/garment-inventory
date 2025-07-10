package org.example.fabricflowbackend.Domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockAdjustment {
    private UUID id;
    private UUID itemId; // Can be variant or raw material
    private String itemType; // VARIANT or RAW_MATERIAL
    private int quantityBefore;
    private int quantityAfter;
    private int adjustmentAmount;
    private String reason;
    private String adjustedBy;
    private LocalDateTime adjustmentDate;

    public StockAdjustment() {
        this.adjustmentDate = LocalDateTime.now();
    }

    public StockAdjustment(UUID itemId, String itemType, int quantityBefore, int quantityAfter, String reason, String adjustedBy) {
        this();
        this.itemId = itemId;
        this.itemType = itemType;
        this.quantityBefore = quantityBefore;
        this.quantityAfter = quantityAfter;
        this.adjustmentAmount = quantityAfter - quantityBefore;
        this.reason = reason;
        this.adjustedBy = adjustedBy;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getItemId() { return itemId; }
    public void setItemId(UUID itemId) { this.itemId = itemId; }

    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }

    public int getQuantityBefore() { return quantityBefore; }
    public void setQuantityBefore(int quantityBefore) { this.quantityBefore = quantityBefore; }

    public int getQuantityAfter() { return quantityAfter; }
    public void setQuantityAfter(int quantityAfter) { this.quantityAfter = quantityAfter; }

    public int getAdjustmentAmount() { return adjustmentAmount; }
    public void setAdjustmentAmount(int adjustmentAmount) { this.adjustmentAmount = adjustmentAmount; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getAdjustedBy() { return adjustedBy; }
    public void setAdjustedBy(String adjustedBy) { this.adjustedBy = adjustedBy; }

    public LocalDateTime getAdjustmentDate() { return adjustmentDate; }
    public void setAdjustmentDate(LocalDateTime adjustmentDate) { this.adjustmentDate = adjustmentDate; }
}