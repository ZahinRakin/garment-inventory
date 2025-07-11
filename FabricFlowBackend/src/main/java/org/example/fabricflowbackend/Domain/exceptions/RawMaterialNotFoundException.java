package org.example.fabricflowbackend.Domain.exceptions;

import java.util.UUID;

public class RawMaterialNotFoundException extends RuntimeException {
    private final UUID materialId;

    public RawMaterialNotFoundException(UUID materialId) {
        super(String.format("Raw material with ID %s not found", materialId));
        this.materialId = materialId;
    }

    public UUID getMaterialId() { return materialId; }
}
