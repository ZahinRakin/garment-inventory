package org.example.fabricflowbackend.infrastructure.repositories;

import org.example.fabricflowbackend.infrastructure.persistence.StockAdjustmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface StockAdjustmentJpaRepository extends JpaRepository<StockAdjustmentEntity, UUID> {
    List<StockAdjustmentEntity> findByItemId(UUID itemId);
    List<StockAdjustmentEntity> findByItemType(String itemType);
    List<StockAdjustmentEntity> findByAdjustmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<StockAdjustmentEntity> findByAdjustedBy(String adjustedBy);
}