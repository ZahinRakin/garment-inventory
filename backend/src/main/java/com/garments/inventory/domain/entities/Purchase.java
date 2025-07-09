package com.garments.inventory.domain.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Purchase {
    @Id
    private UUID id;
    private UUID supplierId;
    private LocalDate orderDate;
    private String status; // CREATED, RECEIVED, CANCELLED
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private transient List<PurchaseItem> purchaseItems;

    public Purchase() {}

    public Purchase(UUID supplierId, LocalDate orderDate, String status, BigDecimal totalAmount) {
        this.id = UUID.randomUUID();
        this.supplierId = supplierId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.createdAt = LocalDateTime.now();
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

    public List<PurchaseItem> getPurchaseItems() { return purchaseItems; }
    public void setPurchaseItems(List<PurchaseItem> purchaseItems) { this.purchaseItems = purchaseItems; }
}
