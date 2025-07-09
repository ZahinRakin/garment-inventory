// domain/repositories/RawMaterialRepository.java
package com.garments.inventory.domain.repositories;

import com.garments.inventory.domain.entities.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, UUID> {
    List<RawMaterial> findByCategory(String category);

    @Query("SELECT r FROM RawMaterial r WHERE r.stock < r.lowStockThreshold")
    List<RawMaterial> findLowStockItems();
}