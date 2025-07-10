package org.example.fabricflowbackend.Domain.services;

import org.example.fabricflowbackend.Domain.entities.RawMaterial;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RawMaterialUseCase {
    RawMaterial createRawMaterial(RawMaterial rawMaterial);
    RawMaterial updateRawMaterial(RawMaterial rawMaterial);
    Optional<RawMaterial> getRawMaterialById(UUID id);
    List<RawMaterial> getAllRawMaterials();
    List<RawMaterial> getRawMaterialsByCategory(String category);
    void deleteRawMaterial(UUID id);

    // Stock operations
    void consumeStock(UUID materialId, int quantity);
    void addStock(UUID materialId, int quantity);
    List<RawMaterial> getMaterialsNeedingReorder();
    List<RawMaterial> getLowStockMaterials(int threshold);
}