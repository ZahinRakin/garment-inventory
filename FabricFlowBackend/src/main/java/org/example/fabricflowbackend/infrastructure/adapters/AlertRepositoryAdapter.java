package org.example.fabricflowbackend.infrastructure.adapters;



import org.example.fabricflowbackend.Domain.entities.Alert;
import org.example.fabricflowbackend.Domain.repositories.AlertRepository;
import org.example.fabricflowbackend.infrastructure.persistence.AlertEntity;
import org.example.fabricflowbackend.infrastructure.repositories.AlertJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class AlertRepositoryAdapter implements AlertRepository {
    private final AlertJpaRepository jpaRepository;

    public AlertRepositoryAdapter(AlertJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Alert save(Alert alert) {
        AlertEntity entity = AlertEntity.fromDomain(alert);
        entity = jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Alert> findById(UUID id) {
        return jpaRepository.findById(id).map(AlertEntity::toDomain);
    }

    @Override
    public List<Alert> findAll() {
        return jpaRepository.findAll().stream()
                .map(AlertEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Alert> findByType(String type) {
        return jpaRepository.findByType(type).stream()
                .map(AlertEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Alert> findBySeverity(String severity) {
        return jpaRepository.findBySeverity(severity).stream()
                .map(AlertEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Alert> findByIsRead(boolean isRead) {
        return jpaRepository.findByIsRead(isRead).stream()
                .map(AlertEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Alert> findByRelatedEntityId(UUID relatedEntityId) {
        return jpaRepository.findByRelatedEntityId(relatedEntityId).stream()
                .map(AlertEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Alert> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return jpaRepository.findByCreatedAtBetween(startDate, endDate).stream()
                .map(AlertEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Alert> findUnreadAlerts() {
        return jpaRepository.findUnreadAlerts().stream()
                .map(AlertEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Alert> findCriticalAlerts() {
        return jpaRepository.findCriticalAlerts().stream()
                .map(AlertEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void markAllAsRead() {
        jpaRepository.markAllAsRead();
    }
}
