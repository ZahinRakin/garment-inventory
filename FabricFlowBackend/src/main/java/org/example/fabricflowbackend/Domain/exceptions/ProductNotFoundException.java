package org.example.fabricflowbackend.Domain.exceptions;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    private final UUID productId;

    public ProductNotFoundException(UUID productId) {
        super(String.format("Product with ID %s not found", productId));
        this.productId = productId;
    }

    public UUID getProductId() { return productId; }
}