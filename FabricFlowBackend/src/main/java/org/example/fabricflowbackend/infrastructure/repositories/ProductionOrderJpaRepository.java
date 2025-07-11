package org.example.fabricflowbackend.infrastructure.repositories;

import org.example.fabricflowbackend.infrastructure.persistence.ProductionOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductionOrderJpaRepository extends JpaRepository<ProductionOrderEntity, UUID> {
    List<ProductionOrderEntity> findByVariantId(UUID variantId);
    List<ProductionOrderEntity> findByStatus(String status);

    @Query("SELECT p FROM ProductionOrderEntity p WHERE p.status = 'CREATED'")
    List<ProductionOrderEntity> findPendingOrders();
}