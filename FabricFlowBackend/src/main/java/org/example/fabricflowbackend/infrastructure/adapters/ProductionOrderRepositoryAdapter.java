package org.example.fabricflowbackend.infrastructure.adapters;



import org.example.fabricflowbackend.Domain.entities.ProductionOrder;
import org.example.fabricflowbackend.Domain.repositories.ProductionOrderRepository;
import org.example.fabricflowbackend.infrastructure.persistence.ProductionOrderEntity;
import org.example.fabricflowbackend.infrastructure.repositories.ProductionOrderJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ProductionOrderRepositoryAdapter implements ProductionOrderRepository {
    private final ProductionOrderJpaRepository jpaRepository;

    public ProductionOrderRepositoryAdapter(ProductionOrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ProductionOrder save(ProductionOrder productionOrder) {
        ProductionOrderEntity entity = ProductionOrderEntity.fromDomain(productionOrder);
        entity = jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<ProductionOrder> findById(UUID id) {
        return jpaRepository.findById(id).map(ProductionOrderEntity::toDomain);
    }

    @Override
    public List<ProductionOrder> findAll() {
        return jpaRepository.findAll().stream()
                .map(ProductionOrderEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductionOrder> findByVariantId(UUID variantId) {
        return jpaRepository.findByVariantId(variantId).stream()
                .map(ProductionOrderEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductionOrder> findByStatus(String status) {
        return jpaRepository.findByStatus(status).stream()
                .map(ProductionOrderEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductionOrder> findPendingOrders() {
        return jpaRepository.findPendingOrders().stream()
                .map(ProductionOrderEntity::toDomain)
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