package org.example.fabricflowbackend.application;

import org.example.fabricflowbackend.Domain.entities.SalesItem;
import org.example.fabricflowbackend.Domain.entities.SalesOrder;
import org.example.fabricflowbackend.Domain.entities.Variant;
import org.example.fabricflowbackend.Domain.exceptions.InsufficientStockException;
import org.example.fabricflowbackend.Domain.exceptions.VariantNotFoundException;
import org.example.fabricflowbackend.Domain.repositories.SalesItemRepository;
import org.example.fabricflowbackend.Domain.repositories.SalesOrderRepository;
import org.example.fabricflowbackend.Domain.repositories.VariantRepository;
import org.example.fabricflowbackend.Domain.services.SalesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SalesService implements SalesUseCase {

    private final SalesOrderRepository salesOrderRepository;
    private final SalesItemRepository salesItemRepository;
    private final VariantRepository variantRepository;

    @Autowired
    public SalesService(SalesOrderRepository salesOrderRepository,
                        SalesItemRepository salesItemRepository,
                        VariantRepository variantRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.salesItemRepository = salesItemRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    public SalesOrder createSalesOrder(SalesOrder salesOrder) {
        if (salesOrder.getCustomerName() == null || salesOrder.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }

        if (salesOrder.getOrderDate() == null) {
            salesOrder.setOrderDate(LocalDate.now());
        }

        return salesOrderRepository.save(salesOrder);
    }

    @Override
    public SalesOrder updateSalesOrder(SalesOrder salesOrder) {
        if (!salesOrderRepository.existsById(salesOrder.getId())) {
            throw new IllegalArgumentException("Sales order not found");
        }
        return salesOrderRepository.save(salesOrder);
    }

    @Override
    public Optional<SalesOrder> getSalesOrderById(UUID id) {
        return salesOrderRepository.findById(id);
    }

    @Override
    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    @Override
    public List<SalesOrder> getSalesOrdersByCustomer(String customerName) {
        return salesOrderRepository.findByCustomerName(customerName);
    }

    @Override
    public List<SalesOrder> getSalesOrdersByStatus(String status) {
        return salesOrderRepository.findByStatus(status);
    }

    @Override
    public List<SalesOrder> getSalesOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        return salesOrderRepository.findByOrderDateBetween(startDate, endDate);
    }

    @Override
    public void deleteSalesOrder(UUID id) {
        if (!salesOrderRepository.existsById(id)) {
            throw new IllegalArgumentException("Sales order not found");
        }
        salesOrderRepository.deleteById(id);
    }

    @Override
    public SalesItem addSalesItem(UUID salesOrderId, SalesItem item) {
        if (!salesOrderRepository.existsById(salesOrderId)) {
            throw new IllegalArgumentException("Sales order not found");
        }

        Variant variant = variantRepository.findById(item.getVariantId())
                .orElseThrow(() -> new VariantNotFoundException(item.getVariantId()));

        if (variant.getQuantity() < item.getQuantity()) {
            throw new InsufficientStockException(item.getVariantId(), item.getQuantity(), variant.getQuantity());
        }

        item.setSalesOrderId(salesOrderId);
        return salesItemRepository.save(item);
    }

    @Override
    public void removeSalesItem(UUID itemId) {
        if (!salesItemRepository.existsById(itemId)) {
            throw new IllegalArgumentException("Sales item not found");
        }
        salesItemRepository.deleteById(itemId);
    }

    @Override
    public List<SalesItem> getSalesItems(UUID salesOrderId) {
        return salesItemRepository.findBySalesOrderId(salesOrderId);
    }

    @Override
    public void markSalesOrderAsDelivered(UUID orderId) {
        SalesOrder order = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Sales order not found"));

        if ("DELIVERED".equals(order.getStatus())) {
            throw new IllegalArgumentException("Order is already delivered");
        }

        List<SalesItem> items = salesItemRepository.findBySalesOrderId(orderId);
        for (SalesItem item : items) {
            Variant variant = variantRepository.findById(item.getVariantId())
                    .orElseThrow(() -> new VariantNotFoundException(item.getVariantId()));
            variant.reduceStock(item.getQuantity());
            variantRepository.save(variant);
        }

        order.setStatus("DELIVERED");
        salesOrderRepository.save(order);
    }

    @Override
    public void processSalesOrder(UUID orderId) {
        SalesOrder order = salesOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Sales order not found"));

        if (!"PENDING".equals(order.getStatus())) {
            throw new IllegalArgumentException("Order cannot be processed in its current state");
        }

        order.setStatus("PROCESSING");
        salesOrderRepository.save(order);
    }

    @Override
    public boolean checkStockAvailability(UUID variantId, int quantity) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new VariantNotFoundException(variantId));
        return variant.getQuantity() >= quantity;
    }
}