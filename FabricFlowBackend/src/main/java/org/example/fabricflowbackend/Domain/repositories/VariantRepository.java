package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.Variant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public interface VariantRepository {
    Variant save(Variant variant);
    Optional<Variant> findById(UUID id);
    List<Variant> findAll();
    List<Variant> findByProductId(UUID productId);
    Optional<Variant> findBySku(String sku);
    List<Variant> findByQuantityLessThanEqual(int threshold);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}