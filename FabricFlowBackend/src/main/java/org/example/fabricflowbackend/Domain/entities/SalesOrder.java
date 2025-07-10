package org.example.fabricflowbackend.Domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SalesOrder {
    private UUID id;
    private String customerName;
    private String status; // PENDING, DELIVERED, CANCELLED
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<SalesItem> items;

    public SalesOrder() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.orderDate = LocalDate.now();
        this.status = "PENDING";
        this.totalAmount = BigDecimal.ZERO;
        this.items = new ArrayList<>();
    }

    public SalesOrder(String customerName) {
        this();
        this.customerName = customerName;
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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<SalesItem> getItems() { return items; }
    public void setItems(List<SalesItem> items) { this.items = items; }

    public void addItem(SalesItem item) {
        this.items.add(item);
        item.setSalesOrderId(this.id);
        calculateTotalAmount();
    }

    private void calculateTotalAmount() {
        this.totalAmount = items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}