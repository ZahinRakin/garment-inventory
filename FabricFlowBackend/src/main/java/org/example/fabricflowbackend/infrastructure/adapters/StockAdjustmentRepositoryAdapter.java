package org.example.fabricflowbackend.infrastructure.adapters;



import org.example.fabricflowbackend.Domain.entities.StockAdjustment;
import org.example.fabricflowbackend.Domain.repositories.StockAdjustmentRepository;
import org.example.fabricflowbackend.infrastructure.persistence.StockAdjustmentEntity;
import org.example.fabricflowbackend.infrastructure.repositories.StockAdjustmentJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class StockAdjustmentRepositoryAdapter implements StockAdjustmentRepository {
    private final StockAdjustmentJpaRepository jpaRepository;

    public StockAdjustmentRepositoryAdapter(StockAdjustmentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public StockAdjustment save(StockAdjustment stockAdjustment) {
        StockAdjustmentEntity entity = StockAdjustmentEntity.fromDomain(stockAdjustment);
        entity = jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<StockAdjustment> findById(UUID id) {
        return jpaRepository.findById(id).map(StockAdjustmentEntity::toDomain);
    }

    @Override
    public List<StockAdjustment> findAll() {
        return jpaRepository.findAll().stream()
                .map(StockAdjustmentEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockAdjustment> findByItemId(UUID itemId) {
        return jpaRepository.findByItemId(itemId).stream()
                .map(StockAdjustmentEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockAdjustment> findByItemType(String itemType) {
        return jpaRepository.findByItemType(itemType).stream()
                .map(StockAdjustmentEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockAdjustment> findByAdjustmentDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaRepository.findByAdjustmentDateBetween(startDate, endDate).stream()
                .map(StockAdjustmentEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<StockAdjustment> findByAdjustedBy(String adjustedBy) {
        return jpaRepository.findByAdjustedBy(adjustedBy).stream()
                .map(StockAdjustmentEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}