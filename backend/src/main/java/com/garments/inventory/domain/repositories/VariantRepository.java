package com.garments.inventory.domain.repositories;

import com.garments.inventory.domain.entities.Variant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VariantRepository {
    Variant save(Variant variant);
    Optional<Variant> findById(UUID id);
    List<Variant> findAll();
    List<Variant> findByProductId(UUID productId);
    void deleteById(UUID id);
}