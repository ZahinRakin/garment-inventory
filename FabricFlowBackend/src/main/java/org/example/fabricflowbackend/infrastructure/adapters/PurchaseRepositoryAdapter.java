package org.example.fabricflowbackend.infrastructure.adapters;



import org.example.fabricflowbackend.Domain.entities.Purchase;
import org.example.fabricflowbackend.Domain.repositories.PurchaseRepository;
import org.example.fabricflowbackend.infrastructure.persistence.PurchaseEntity;
import org.example.fabricflowbackend.infrastructure.repositories.PurchaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PurchaseRepositoryAdapter implements PurchaseRepository {
    private final PurchaseJpaRepository jpaRepository;

    public PurchaseRepositoryAdapter(PurchaseJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Purchase save(Purchase purchase) {
        PurchaseEntity entity = PurchaseEntity.fromDomain(purchase);
        entity = jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Purchase> findById(UUID id) {
        return jpaRepository.findById(id).map(PurchaseEntity::toDomain);
    }

    @Override
    public List<Purchase> findAll() {
        return jpaRepository.findAll().stream()
                .map(PurchaseEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Purchase> findBySupplierId(UUID supplierId) {
        return jpaRepository.findBySupplierId(supplierId).stream()
                .map(PurchaseEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Purchase> findByStatus(String status) {
        return jpaRepository.findByStatus(status).stream()
                .map(PurchaseEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Purchase> findByOrderDateBetween(LocalDate startDate, LocalDate endDate) {
        return jpaRepository.findByOrderDateBetween(startDate, endDate).stream()
                .map(PurchaseEntity::toDomain)
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