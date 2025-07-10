package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.Alert;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public interface AlertRepository {
    Alert save(Alert alert);
    Optional<Alert> findById(UUID id);
    List<Alert> findAll();
    List<Alert> findByType(String type);
    List<Alert> findBySeverity(String severity);
    List<Alert> findByIsRead(boolean isRead);
    List<Alert> findByRelatedEntityId(UUID relatedEntityId);
    List<Alert> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Alert> findUnreadAlerts();
    List<Alert> findCriticalAlerts();
    void deleteById(UUID id);
    void markAllAsRead();
}