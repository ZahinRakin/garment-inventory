package com.garments.inventory.domain.repositories;

import com.garments.inventory.domain.entities.Purchase;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PurchaseRepository {
    Purchase save(Purchase purchase);
    Optional<Purchase> findById(UUID id);
    List<Purchase> findAll();
    List<Purchase> findBySupplierId(UUID supplierId);
    List<Purchase> findByStatus(String status);
    void deleteById(UUID id);
}