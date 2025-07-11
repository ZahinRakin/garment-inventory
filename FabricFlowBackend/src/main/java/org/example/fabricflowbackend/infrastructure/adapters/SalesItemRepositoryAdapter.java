package org.example.fabricflowbackend.infrastructure.adapters;



import org.example.fabricflowbackend.Domain.entities.SalesItem;
import org.example.fabricflowbackend.Domain.repositories.SalesItemRepository;
import org.example.fabricflowbackend.infrastructure.persistence.SalesItemEntity;
import org.example.fabricflowbackend.infrastructure.repositories.SalesItemJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class SalesItemRepositoryAdapter implements SalesItemRepository {
    private final SalesItemJpaRepository jpaRepository;

    public SalesItemRepositoryAdapter(SalesItemJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public SalesItem save(SalesItem salesItem) {
        SalesItemEntity entity = SalesItemEntity.fromDomain(salesItem);
        entity = jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<SalesItem> findById(UUID id) {
        return jpaRepository.findById(id).map(SalesItemEntity::toDomain);
    }

    @Override
    public List<SalesItem> findAll() {
        return jpaRepository.findAll().stream()
                .map(SalesItemEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesItem> findBySalesOrderId(UUID salesOrderId) {
        return jpaRepository.findBySalesOrderId(salesOrderId).stream()
                .map(SalesItemEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesItem> findByVariantId(UUID variantId) {
        return jpaRepository.findByVariantId(variantId).stream()
                .map(SalesItemEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}