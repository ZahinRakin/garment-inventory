// domain/repositories/RawMaterialRepository.java
package com.garments.inventory.domain.repositories;

import com.garments.inventory.domain.entities.RawMaterial;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RawMaterialRepository {
    RawMaterial save(RawMaterial rawMaterial);
    Optional<RawMaterial> findById(UUID id);
    List<RawMaterial> findAll();
    List<RawMaterial> findByCategory(String category);
    List<RawMaterial> findLowStockItems();
    void deleteById(UUID id);
}