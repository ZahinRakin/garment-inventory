package com.garments.inventory.domain.repositories;

import com.garments.inventory.domain.entities.Variant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface VariantRepository extends JpaRepository<Variant, UUID> {
    List<Variant> findByProductId(UUID productId);
    // You can add custom query methods here if needed
}