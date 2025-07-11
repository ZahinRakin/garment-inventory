package org.example.fabricflowbackend.infrastructure.adapters;

import org.example.fabricflowbackend.Domain.entities.Supplier;
import org.example.fabricflowbackend.Domain.repositories.SupplierRepository;
import org.example.fabricflowbackend.infrastructure.persistence.SupplierEntity;
import org.example.fabricflowbackend.infrastructure.repositories.SupplierJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class SupplierRepositoryAdapter implements SupplierRepository {
    private final SupplierJpaRepository jpaRepository;

    public SupplierRepositoryAdapter(SupplierJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Supplier save(Supplier supplier) {
        SupplierEntity entity = SupplierEntity.fromDomain(supplier);
        entity = jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Supplier> findById(UUID id) {
        return jpaRepository.findById(id).map(SupplierEntity::toDomain);
    }

    @Override
    public List<Supplier> findAll() {
        return jpaRepository.findAll().stream()
                .map(SupplierEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Supplier> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(SupplierEntity::toDomain);
    }

    @Override
    public List<Supplier> findByNameContaining(String name) {
        return jpaRepository.findByNameContaining(name).stream()
                .map(SupplierEntity::toDomain)
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