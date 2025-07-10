package org.example.fabricflowbackend.application.dto.stock;



import java.util.UUID;

public class StockAdjustmentRequestDTO {
    private UUID itemId;
    private int quantity;
    private String reason;
    private boolean isRawMaterial; // true for raw material, false for variant

    // Getters and Setters
    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isRawMaterial() {
        return isRawMaterial;
    }

    public void setRawMaterial(boolean rawMaterial) {
        isRawMaterial = rawMaterial;
    }
}