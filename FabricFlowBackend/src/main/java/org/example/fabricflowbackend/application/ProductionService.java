package org.example.fabricflowbackend.application;

import org.example.fabricflowbackend.Domain.entities.ProductionOrder;
import org.example.fabricflowbackend.Domain.entities.Variant;
import org.example.fabricflowbackend.Domain.exceptions.InvalidOperationException;
import org.example.fabricflowbackend.Domain.exceptions.VariantNotFoundException;
import org.example.fabricflowbackend.Domain.repositories.ProductionOrderRepository;
import org.example.fabricflowbackend.Domain.repositories.VariantRepository;
import org.example.fabricflowbackend.Domain.services.ProductionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProductionService implements ProductionUseCase {

    private final ProductionOrderRepository productionOrderRepository;
    private final VariantRepository variantRepository;

    public ProductionService(ProductionOrderRepository productionOrderRepository,
                                 VariantRepository variantRepository) {
        this.productionOrderRepository = productionOrderRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    public ProductionOrder createProductionOrder(ProductionOrder productionOrder) {
        if (!variantRepository.existsById(productionOrder.getVariantId())) {
            throw new VariantNotFoundException(productionOrder.getVariantId());
        }

        if (productionOrder.getQuantity() <= 0) {
            throw new IllegalArgumentException("Production quantity must be positive");
        }

        return productionOrderRepository.save(productionOrder);
    }

    @Override
    public ProductionOrder updateProductionOrder(ProductionOrder productionOrder) {
        if (!productionOrderRepository.existsById(productionOrder.getId())) {
            throw new InvalidOperationException("Production order not found with id: " + productionOrder.getId());
        }

        return productionOrderRepository.save(productionOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductionOrder> getProductionOrderById(UUID id) {
        return productionOrderRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionOrder> getAllProductionOrders() {
        return productionOrderRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionOrder> getProductionOrdersByVariant(UUID variantId) {
        return productionOrderRepository.findByVariantId(variantId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionOrder> getProductionOrdersByStatus(String status) {
        return productionOrderRepository.findByStatus(status);
    }

    @Override
    public void deleteProductionOrder(UUID id) {
        if (!productionOrderRepository.existsById(id)) {
            throw new InvalidOperationException("Production order not found with id: " + id);
        }

        ProductionOrder order = productionOrderRepository.findById(id).get();
        if (order.isCompleted()) {
            throw new InvalidOperationException("Cannot delete completed production order");
        }

        productionOrderRepository.deleteById(id);
    }

    @Override
    public void startProduction(UUID orderId) {
        ProductionOrder order = productionOrderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidOperationException("Production order not found with id: " + orderId));

        if (order.isInProgress() || order.isCompleted()) {
            throw new InvalidOperationException("Production order is already started or completed");
        }

        order.startProduction();
        productionOrderRepository.save(order);
    }

    @Override
    public void completeProduction(UUID orderId) {
        ProductionOrder order = productionOrderRepository.findById(orderId)
                .orElseThrow(() -> new InvalidOperationException("Production order not found with id: " + orderId));

        if (order.isCompleted()) {
            throw new InvalidOperationException("Production order is already completed");
        }

        // Update variant stock
        Variant variant = variantRepository.findById(order.getVariantId())
                .orElseThrow(() -> new VariantNotFoundException(order.getVariantId()));

        variant.increaseStock(order.getQuantity());
        variantRepository.save(variant);

        order.completeProduction();
        productionOrderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionOrder> getPendingOrders() {
        return productionOrderRepository.findByStatus("CREATED");
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductionOrder> getInProgressOrders() {
        return productionOrderRepository.findByStatus("IN_PROGRESS");
    }
}

