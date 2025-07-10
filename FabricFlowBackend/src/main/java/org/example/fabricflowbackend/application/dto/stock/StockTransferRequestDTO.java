package org.example.fabricflowbackend.application.dto.stock;



import java.util.UUID;

public class StockTransferRequestDTO {
    private UUID fromId;
    private UUID toId;
    private int quantity;
    private boolean isRawMaterial; // true for raw material, false for variant

    // Getters and Setters
    public UUID getFromId() {
        return fromId;
    }

    public void setFromId(UUID fromId) {
        this.fromId = fromId;
    }

    public UUID getToId() {
        return toId;
    }

    public void setToId(UUID toId) {
        this.toId = toId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isRawMaterial() {
        return isRawMaterial;
    }

    public void setRawMaterial(boolean rawMaterial) {
        isRawMaterial = rawMaterial;
    }
}