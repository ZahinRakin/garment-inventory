package org.example.fabricflowbackend.infrastructure.adapters;

import org.example.fabricflowbackend.Domain.entities.Variant;
import org.example.fabricflowbackend.Domain.repositories.VariantRepository;
import org.example.fabricflowbackend.infrastructure.persistence.VariantEntity;
import org.example.fabricflowbackend.infrastructure.repositories.VariantJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class VariantRepositoryAdapter implements VariantRepository {
    private final VariantJpaRepository jpaRepository;

    public VariantRepositoryAdapter(VariantJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Variant save(Variant variant) {
        VariantEntity entity = VariantEntity.fromDomain(variant);
        entity = jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Variant> findById(UUID id) {
        return jpaRepository.findById(id).map(VariantEntity::toDomain);
    }

    @Override
    public List<Variant> findAll() {
        return jpaRepository.findAll().stream()
                .map(VariantEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Variant> findByProductId(UUID productId) {
        return jpaRepository.findByProductId(productId).stream()
                .map(VariantEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Variant> findBySku(String sku) {
        return jpaRepository.findBySku(sku).map(VariantEntity::toDomain);
    }

    @Override
    public List<Variant> findByQuantityLessThanEqual(int threshold) {
        return jpaRepository.findByQuantityLessThanEqual(threshold).stream()
                .map(VariantEntity::toDomain)
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