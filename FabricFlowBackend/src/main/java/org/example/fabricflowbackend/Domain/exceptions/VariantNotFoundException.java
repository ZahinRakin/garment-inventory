package org.example.fabricflowbackend.Domain.exceptions;

import java.util.UUID;

public class VariantNotFoundException extends RuntimeException {
    private final UUID variantId;

    public VariantNotFoundException(UUID variantId) {
        super(String.format("Variant with ID %s not found", variantId));
        this.variantId = variantId;
    }

    public UUID getVariantId() { return variantId; }
}