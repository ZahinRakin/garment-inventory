package com.garments.inventory.domain.repositories;

import com.garments.inventory.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
    // You can add custom query methods here if needed
}
