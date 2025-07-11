package org.example.fabricflowbackend.infrastructure.repositories;

import org.example.fabricflowbackend.infrastructure.persistence.SalesItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface SalesItemJpaRepository extends JpaRepository<SalesItemEntity, UUID> {
    List<SalesItemEntity> findBySalesOrderId(UUID salesOrderId);
    List<SalesItemEntity> findByVariantId(UUID variantId);
}