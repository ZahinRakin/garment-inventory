package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.SalesOrder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SalesOrderRepository {
    SalesOrder save(SalesOrder salesOrder);
    Optional<SalesOrder> findById(UUID id);
    List<SalesOrder> findAll();
    List<SalesOrder> findByStatus(String status);
    List<SalesOrder> findByCustomerName(String customerName);
    void deleteById(UUID id);
}