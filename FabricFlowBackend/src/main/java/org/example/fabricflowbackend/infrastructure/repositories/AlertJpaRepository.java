package org.example.fabricflowbackend.infrastructure.repositories;

import org.example.fabricflowbackend.infrastructure.persistence.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AlertJpaRepository extends JpaRepository<AlertEntity, UUID> {
    List<AlertEntity> findByType(String type);
    List<AlertEntity> findBySeverity(String severity);
    List<AlertEntity> findByIsRead(boolean isRead);
    List<AlertEntity> findByRelatedEntityId(UUID relatedEntityId);
    List<AlertEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT a FROM AlertEntity a WHERE a.isRead = false")
    List<AlertEntity> findUnreadAlerts();

    @Query("SELECT a FROM AlertEntity a WHERE a.severity = 'CRITICAL'")
    List<AlertEntity> findCriticalAlerts();

    @Modifying
    @Query("UPDATE AlertEntity a SET a.isRead = true, a.readAt = CURRENT_TIMESTAMP")
    void markAllAsRead();
}
