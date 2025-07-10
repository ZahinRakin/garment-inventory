package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.RawMaterial;
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