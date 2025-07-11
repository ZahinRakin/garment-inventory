package org.example.fabricflowbackend.Domain.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Alert {
    private UUID id;
    private String type; // LOW_STOCK, REORDER_NEEDED, PRODUCTION_DELAYED, etc.
    private String message;
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL
    private UUID relatedEntityId;
    private String relatedEntityType;
    private boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;

    public Alert() {
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }

    public Alert(String type, String message, String severity, UUID relatedEntityId, String relatedEntityType) {
        this();
        this.type = type;
        this.message = message;
        this.severity = severity;
        this.relatedEntityId = relatedEntityId;
        this.relatedEntityType = relatedEntityType;
    }

    // Business methods
    public void markAsRead() {
        this.isRead = true;
        this.readAt = LocalDateTime.now();
    }

    public boolean isCritical() {
        return "CRITICAL".equals(severity);
    }

    public boolean isHigh() {
        return "HIGH".equals(severity);
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public UUID getRelatedEntityId() { return relatedEntityId; }
    public void setRelatedEntityId(UUID relatedEntityId) { this.relatedEntityId = relatedEntityId; }

    public String getRelatedEntityType() { return relatedEntityType; }
    public void setRelatedEntityType(String relatedEntityType) { this.relatedEntityType = relatedEntityType; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getReadAt() { return readAt; }
    public void setReadAt(LocalDateTime readAt) { this.readAt = readAt; }
}
