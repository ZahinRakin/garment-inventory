package org.example.fabricflowbackend.application.dto.report;

import java.util.UUID;

public class SupplierReportRequestDTO extends DateRangeRequestDTO {
    private UUID supplierId;

    // Getters and Setters
    public UUID getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(UUID supplierId) {
        this.supplierId = supplierId;
    }
}
