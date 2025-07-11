package org.example.fabricflowbackend.infrastructure.repositories;

import org.example.fabricflowbackend.infrastructure.persistence.PurchaseItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PurchaseItemJpaRepository extends JpaRepository<PurchaseItemEntity, UUID> {
    List<PurchaseItemEntity> findByPurchaseId(UUID purchaseId);
    List<PurchaseItemEntity> findByMaterialId(UUID materialId);
}