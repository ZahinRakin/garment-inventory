package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.Variant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VariantRepository {
    Variant save(Variant variant);
    Optional<Variant> findById(UUID id);
    List<Variant> findAll();
    List<Variant> findByProductId(UUID productId);
    List<Variant> findByQuantityLessThan(Integer quantity);
    void deleteById(UUID id);
}