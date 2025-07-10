package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.RawMaterial;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public interface RawMaterialRepository {
    RawMaterial save(RawMaterial rawMaterial);
    Optional<RawMaterial> findById(UUID id);
    List<RawMaterial> findAll();
    List<RawMaterial> findByCategory(String category);
    List<RawMaterial> findByCurrentStockLessThanEqual(int threshold);
    List<RawMaterial> findMaterialsNeedingReorder();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}
