package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.ProductionOrder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public interface ProductionOrderRepository {
    ProductionOrder save(ProductionOrder productionOrder);
    Optional<ProductionOrder> findById(UUID id);
    List<ProductionOrder> findAll();
    List<ProductionOrder> findByVariantId(UUID variantId);
    List<ProductionOrder> findByStatus(String status);
    List<ProductionOrder> findPendingOrders();
    void deleteById(UUID id);
    boolean existsById(UUID id);
}

