package org.example.fabricflowbackend.infrastructure.repositories;

import org.example.fabricflowbackend.infrastructure.persistence.SalesOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface SalesOrderJpaRepository extends JpaRepository<SalesOrderEntity, UUID> {
    List<SalesOrderEntity> findByCustomerName(String customerName);
    List<SalesOrderEntity> findByStatus(String status);
    List<SalesOrderEntity> findByOrderDateBetween(LocalDate startDate, LocalDate endDate);
}
