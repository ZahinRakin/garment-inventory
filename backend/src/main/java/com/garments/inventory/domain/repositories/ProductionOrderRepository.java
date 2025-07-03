package com.garments.inventory.domain.repositories;

import com.garments.inventory.domain.entities.ProductionOrder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductionOrderRepository {
    ProductionOrder save(ProductionOrder productionOrder);
    Optional<ProductionOrder> findById(UUID id);
    List<ProductionOrder> findAll();
    List<ProductionOrder> findByStatus(String status);
    void deleteById(UUID id);
}
