package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.SalesOrder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public interface SalesOrderRepository {
    SalesOrder save(SalesOrder salesOrder);
    Optional<SalesOrder> findById(UUID id);
    List<SalesOrder> findAll();
    List<SalesOrder> findByCustomerName(String customerName);
    List<SalesOrder> findByStatus(String status);
    List<SalesOrder> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}

