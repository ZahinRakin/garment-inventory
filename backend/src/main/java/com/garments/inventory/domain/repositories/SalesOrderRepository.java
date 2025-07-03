package com.garments.inventory.domain.repositories;

import com.garments.inventory.domain.entities.SalesOrder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SalesOrderRepository {
    SalesOrder save(SalesOrder salesOrder);
    Optional<SalesOrder> findById(UUID id);
    List<SalesOrder> findAll();
    List<SalesOrder> findByStatus(String status);
    void deleteById(UUID id);
}