package org.example.fabricflowbackend.Domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Purchase {
    private UUID id;
    private UUID supplierId;
    private LocalDate orderDate;
    private String status; // CREATED, RECEIVED, CANCELLED
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<PurchaseItem> items;

    public Purchase() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.orderDate = LocalDate.now();
        this.status = "CREATED";
        this.totalAmount = BigDecimal.ZERO;
        this.items = new ArrayList<>();
    }

    public Purchase(UUID supplierId) {
        this();
        this.supplierId = supplierId;
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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<PurchaseItem> getItems() { return items; }
    public void setItems(List<PurchaseItem> items) { this.items = items; }

    public void addItem(PurchaseItem item) {
        this.items.add(item);
        item.setPurchaseId(this.id);
        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        this.totalAmount = items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}