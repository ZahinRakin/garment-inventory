package org.example.fabricflowbackend.Domain.repositories;

import org.ecample.fabricflowbackend.Domain.entities.ProductionOrder;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductionOrderRepository {
    ProductionOrder save(ProductionOrder productionOrder);
    Optional<ProductionOrder> findById(UUID id);
    List<ProductionOrder> findAll();
    List<ProductionOrder> findByStatus(String status);
    List<ProductionOrder> findByVariantId(UUID variantId);
    List<ProductionOrder> findByStartDateBetween(LocalDate startDate, LocalDate endDate);
    List<ProductionOrder> findByEndDateBetween(LocalDate startDate, LocalDate endDate);
    void deleteById(UUID id);
}