package org.example.fabricflowbackend.Domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SalesOrder {
    private UUID id;
    private String customerName;
    private String status; // PENDING, DELIVERED, CANCELLED
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private List<SalesItem> items;
    private LocalDateTime createdAt;

    public SalesOrder() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";
    }

    public SalesOrder(String customerName, LocalDate orderDate) {
        this();
        this.customerName = customerName;
        this.orderDate = orderDate;
    }

    // Business logic methods
    public void markAsDelivered() {
        this.status = "DELIVERED";
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

    public boolean isDelivered() {
        return "DELIVERED".equals(status);
    }

    public boolean isPending() {
        return "PENDING".equals(status);
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDate orderDate) { this.orderDate = orderDate; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public List<SalesItem> getItems() { return items; }
    public void setItems(List<SalesItem> items) { this.items = items; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}