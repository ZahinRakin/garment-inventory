package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.StockAdjustment;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public interface StockAdjustmentRepository {
    StockAdjustment save(StockAdjustment stockAdjustment);
    Optional<StockAdjustment> findById(UUID id);
    List<StockAdjustment> findAll();
    List<StockAdjustment> findByItemId(UUID itemId);
    List<StockAdjustment> findByItemType(String itemType);
    List<StockAdjustment> findByAdjustmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<StockAdjustment> findByAdjustedBy(String adjustedBy);
    void deleteById(UUID id);
}
