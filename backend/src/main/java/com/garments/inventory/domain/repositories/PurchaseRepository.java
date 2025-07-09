package com.garments.inventory.domain.repositories;

import com.garments.inventory.domain.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
    // You can add custom query methods here if needed
}