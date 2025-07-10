package org.example.fabricflowbackend.infrastructure.adapters;



import org.example.fabricflowbackend.Domain.entities.SalesOrder;
import org.example.fabricflowbackend.Domain.repositories.SalesOrderRepository;
import org.example.fabricflowbackend.infrastructure.persistence.SalesOrderEntity;
import org.example.fabricflowbackend.infrastructure.repositories.SalesOrderJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class SalesOrderRepositoryAdapter implements SalesOrderRepository {
    private final SalesOrderJpaRepository jpaRepository;

    public SalesOrderRepositoryAdapter(SalesOrderJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public SalesOrder save(SalesOrder salesOrder) {
        SalesOrderEntity entity = SalesOrderEntity.fromDomain(salesOrder);
        entity = jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<SalesOrder> findById(UUID id) {
        return jpaRepository.findById(id).map(SalesOrderEntity::toDomain);
    }

    @Override
    public List<SalesOrder> findAll() {
        return jpaRepository.findAll().stream()
                .map(SalesOrderEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesOrder> findByCustomerName(String customerName) {
        return jpaRepository.findByCustomerName(customerName).stream()
                .map(SalesOrderEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesOrder> findByStatus(String status) {
        return jpaRepository.findByStatus(status).stream()
                .map(SalesOrderEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesOrder> findByOrderDateBetween(LocalDate startDate, LocalDate endDate) {
        return jpaRepository.findByOrderDateBetween(startDate, endDate).stream()
                .map(SalesOrderEntity::toDomain)
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