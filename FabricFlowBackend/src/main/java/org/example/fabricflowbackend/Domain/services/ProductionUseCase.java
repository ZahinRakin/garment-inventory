package org.example.fabricflowbackend.Domain.services;

import org.example.fabricflowbackend.Domain.entities.ProductionOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductionUseCase {
    ProductionOrder createProductionOrder(ProductionOrder productionOrder);
    ProductionOrder updateProductionOrder(ProductionOrder productionOrder);
    Optional<ProductionOrder> getProductionOrderById(UUID id);
    List<ProductionOrder> getAllProductionOrders();
    List<ProductionOrder> getProductionOrdersByVariant(UUID variantId);
    List<ProductionOrder> getProductionOrdersByStatus(String status);
    void deleteProductionOrder(UUID id);

    // Business operations
    void startProduction(UUID orderId);
    void completeProduction(UUID orderId);
    List<ProductionOrder> getPendingOrders();
    List<ProductionOrder> getInProgressOrders();
}

