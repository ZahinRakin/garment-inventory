package org.example.fabricflowbackend.infrastructure.persistence;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.Alert;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "alerts")
@Getter
@Setter
public class AlertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    private String severity;

    @Column(name = "related_entity_id", nullable = false)
    private UUID relatedEntityId;

    @Column(name = "related_entity_type", nullable = false)
    private String relatedEntityType;

    @Column(nullable = false)
    private boolean isRead;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime readAt;

    public Alert toDomain() {
        Alert alert = new Alert(type, message, severity, relatedEntityId, relatedEntityType);
        alert.setId(id);
        alert.setRead(isRead);
        alert.setCreatedAt(createdAt);
        alert.setReadAt(readAt);
        return alert;
    }

    public static AlertEntity fromDomain(Alert alert) {
        AlertEntity entity = new AlertEntity();
        entity.setId(alert.getId());
        entity.setType(alert.getType());
        entity.setMessage(alert.getMessage());
        entity.setSeverity(alert.getSeverity());
        entity.setRelatedEntityId(alert.getRelatedEntityId());
        entity.setRelatedEntityType(alert.getRelatedEntityType());
        entity.setRead(alert.isRead());
        entity.setCreatedAt(alert.getCreatedAt());
        entity.setReadAt(alert.getReadAt());
        return entity;
    }
}