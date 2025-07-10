package org.example.fabricflowbackend.Domain.services;

import org.example.fabricflowbackend.Domain.entities.SalesItem;
import org.example.fabricflowbackend.Domain.entities.SalesOrder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SalesUseCase {
    SalesOrder createSalesOrder(SalesOrder salesOrder);
    SalesOrder updateSalesOrder(SalesOrder salesOrder);
    Optional<SalesOrder> getSalesOrderById(UUID id);
    List<SalesOrder> getAllSalesOrders();
    List<SalesOrder> getSalesOrdersByCustomer(String customerName);
    List<SalesOrder> getSalesOrdersByStatus(String status);
    List<SalesOrder> getSalesOrdersByDateRange(LocalDate startDate, LocalDate endDate);
    void deleteSalesOrder(UUID id);

    // Sales item operations
    SalesItem addSalesItem(UUID salesOrderId, SalesItem item);
    void removeSalesItem(UUID itemId);
    List<SalesItem> getSalesItems(UUID salesOrderId);

    // Business operations
    void markSalesOrderAsDelivered(UUID orderId);
    void processSalesOrder(UUID orderId);
    boolean checkStockAvailability(UUID variantId, int quantity);
}
