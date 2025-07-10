package org.example.fabricflowbackend.Domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Purchase {
    private UUID id;
    private UUID supplierId;
    private LocalDate orderDate;
    private String status; // CREATED, RECEIVED, CANCELLED
    private BigDecimal totalAmount;
    private List<PurchaseItem> items;
    private LocalDateTime createdAt;

    public Purchase() {
        this.createdAt = LocalDateTime.now();
        this.status = "CREATED";
    }

    public Purchase(UUID supplierId, LocalDate orderDate) {
        this();
        this.supplierId = supplierId;
        this.orderDate = orderDate;
    }

    // Business logic methods
    public void markAsReceived() {
        this.status = "RECEIVED";
    }

    public void markAsCancelled() {
        this.status = "CANCELLED";
    }

    public void calculateTotalAmount() {
        if (items != null && !items.isEmpty()) {
            this.totalAmount = items.stream()
                    .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
    }

    public boolean isCompleted() {
        return "RECEIVED".equals(status);
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getSupplierId() { return supplierId; }
    public void setSupplierId(UUID supplierId) { this.supplierId = supplierId; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public List<PurchaseItem> getItems() { return items; }
    public void setItems(List<PurchaseItem> items) { this.items = items; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
