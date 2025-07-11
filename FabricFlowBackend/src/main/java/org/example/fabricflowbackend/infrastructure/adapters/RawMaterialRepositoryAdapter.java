package org.example.fabricflowbackend.infrastructure.adapters;



import org.example.fabricflowbackend.Domain.entities.RawMaterial;
import org.example.fabricflowbackend.Domain.repositories.RawMaterialRepository;
import org.example.fabricflowbackend.infrastructure.persistence.RawMaterialEntity;
import org.example.fabricflowbackend.infrastructure.repositories.RawMaterialJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RawMaterialRepositoryAdapter implements RawMaterialRepository {
    private final RawMaterialJpaRepository jpaRepository;

    public RawMaterialRepositoryAdapter(RawMaterialJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public RawMaterial save(RawMaterial rawMaterial) {
        RawMaterialEntity entity = RawMaterialEntity.fromDomain(rawMaterial);
        entity = jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<RawMaterial> findById(UUID id) {
        return jpaRepository.findById(id).map(RawMaterialEntity::toDomain);
    }

    @Override
    public List<RawMaterial> findAll() {
        return jpaRepository.findAll().stream()
                .map(RawMaterialEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<RawMaterial> findByCategory(String category) {
        return jpaRepository.findByCategory(category).stream()
                .map(RawMaterialEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<RawMaterial> findByCurrentStockLessThanEqual(int threshold) {
        return jpaRepository.findByCurrentStockLessThanEqual(threshold).stream()
                .map(RawMaterialEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<RawMaterial> findMaterialsNeedingReorder() {
        return jpaRepository.findMaterialsNeedingReorder().stream()
                .map(RawMaterialEntity::toDomain)
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
