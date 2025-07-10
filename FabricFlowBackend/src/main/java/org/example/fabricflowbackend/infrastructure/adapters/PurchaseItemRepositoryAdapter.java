package org.example.fabricflowbackend.infrastructure.adapters;



import org.example.fabricflowbackend.Domain.entities.PurchaseItem;
import org.example.fabricflowbackend.Domain.repositories.PurchaseItemRepository;
import org.example.fabricflowbackend.infrastructure.persistence.PurchaseItemEntity;
import org.example.fabricflowbackend.infrastructure.repositories.PurchaseItemJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PurchaseItemRepositoryAdapter implements PurchaseItemRepository {
    private final PurchaseItemJpaRepository jpaRepository;

    public PurchaseItemRepositoryAdapter(PurchaseItemJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public PurchaseItem save(PurchaseItem purchaseItem) {
        PurchaseItemEntity entity = PurchaseItemEntity.fromDomain(purchaseItem);
        entity = jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<PurchaseItem> findById(UUID id) {
        return jpaRepository.findById(id).map(PurchaseItemEntity::toDomain);
    }

    @Override
    public List<PurchaseItem> findAll() {
        return jpaRepository.findAll().stream()
                .map(PurchaseItemEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseItem> findByPurchaseId(UUID purchaseId) {
        return jpaRepository.findByPurchaseId(purchaseId).stream()
                .map(PurchaseItemEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseItem> findByMaterialId(UUID materialId) {
        return jpaRepository.findByMaterialId(materialId).stream()
                .map(PurchaseItemEntity::toDomain)
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