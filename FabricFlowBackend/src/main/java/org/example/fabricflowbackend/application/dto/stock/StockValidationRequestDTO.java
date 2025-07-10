package org.example.fabricflowbackend.application.dto.stock;


import java.util.UUID;

public class StockValidationRequestDTO {
    private UUID itemId;
    private int requiredQuantity;
    private boolean isRawMaterial; // true for raw material, false for variant

    // Getters and Setters
    public UUID getItemId() {
        return itemId;
    }

    public void setItemId(UUID itemId) {
        this.itemId = itemId;
    }

    public int getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(int requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    public boolean isRawMaterial() {
        return isRawMaterial;
    }

    public void setRawMaterial(boolean rawMaterial) {
        isRawMaterial = rawMaterial;
    }
}