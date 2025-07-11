package org.example.fabricflowbackend.Domain.services;

import org.example.fabricflowbackend.Domain.entities.RawMaterial;
import org.example.fabricflowbackend.Domain.entities.Variant;
import org.example.fabricflowbackend.Domain.entities.StockAdjustment;

import java.util.List;
import java.util.UUID;

public interface StockUseCase {
    // Raw material stock operations
    void adjustRawMaterialStock(UUID materialId, int quantity, String reason);
    void transferRawMaterialStock(UUID fromMaterialId, UUID toMaterialId, int quantity);
    List<RawMaterial> getRawMaterialsWithLowStock();

    // Finished goods stock operations
    void adjustVariantStock(UUID variantId, int quantity, String reason);
    void transferVariantStock(UUID fromVariantId, UUID toVariantId, int quantity);
    List<Variant> getVariantsWithLowStock();

    // Stock validation
    boolean validateRawMaterialStock(UUID materialId, int requiredQuantity);
    boolean validateVariantStock(UUID variantId, int requiredQuantity);

    // Stock alerts
    List<String> getStockAlerts();
    void generateStockReport();
    
    // Stock adjustments history
    List<StockAdjustment> getAllStockAdjustments();
}
