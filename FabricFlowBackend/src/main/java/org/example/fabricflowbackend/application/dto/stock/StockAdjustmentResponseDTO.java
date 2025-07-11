package org.example.fabricflowbackend.application.dto.stock;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockAdjustmentResponseDTO {
    private UUID id;
    private UUID itemId;
    private String itemType; // "VARIANT" or "RAW_MATERIAL"
    private int quantityBefore;
    private int quantityAfter;
    private int adjustmentAmount;
    private String reason;
    private String adjustedBy;
    private LocalDateTime adjustmentDate;

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