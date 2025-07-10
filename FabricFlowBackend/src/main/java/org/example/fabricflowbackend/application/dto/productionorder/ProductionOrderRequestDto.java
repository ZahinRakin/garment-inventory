package org.example.fabricflowbackend.application.dto.productionorder;



import java.time.LocalDate;
import java.util.UUID;

public class ProductionOrderRequestDto {
    private UUID variantId;
    private int quantity;
    private LocalDate startDate;
    private LocalDate endDate;

    // Getters and Setters
    public UUID getVariantId() { return variantId; }
    public void setVariantId(UUID variantId) { this.variantId = variantId; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}
