package org.example.fabricflowbackend.infrastructure.repositories;

import org.example.fabricflowbackend.infrastructure.persistence.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseJpaRepository extends JpaRepository<PurchaseEntity, UUID> {
    List<PurchaseEntity> findBySupplierId(UUID supplierId);
    List<PurchaseEntity> findByStatus(String status);
    List<PurchaseEntity> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}