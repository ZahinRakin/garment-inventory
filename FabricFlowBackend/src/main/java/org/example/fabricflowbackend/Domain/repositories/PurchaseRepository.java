package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.Purchase;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public interface PurchaseRepository {
    Purchase save(Purchase purchase);
    Optional<Purchase> findById(UUID id);
    List<Purchase> findAll();
    List<Purchase> findBySupplierId(UUID supplierId);
    List<Purchase> findByStatus(String status);
    List<Purchase> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}
