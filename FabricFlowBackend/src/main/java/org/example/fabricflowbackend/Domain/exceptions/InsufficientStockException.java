package org.example.fabricflowbackend.Domain.exceptions;

import java.util.UUID;

public class InsufficientStockException extends RuntimeException {
    private final UUID itemId;
    private final int requestedQuantity;
    private final int availableQuantity;

    public InsufficientStockException(UUID itemId, int requestedQuantity, int availableQuantity) {
        super(String.format("Insufficient stock for item %s. Requested: %d, Available: %d",
                itemId, requestedQuantity, availableQuantity));
        this.itemId = itemId;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    public UUID getItemId() { return itemId; }
    public int getRequestedQuantity() { return requestedQuantity; }
    public int getAvailableQuantity() { return availableQuantity; }
}
