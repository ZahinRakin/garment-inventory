package com.garments.inventory.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class SalesOrder {
    private UUID id;
    private String customerName;
    private String status; // PENDING, DELIVERED, CANCELLED
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private List<SalesItem> salesItems;

    public SalesOrder() {}

    public SalesOrder(String customerName, String status, LocalDate orderDate, BigDecimal totalAmount) {
        this.id = UUID.randomUUID();
        this.customerName = customerName;
        this.status = status;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
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

    public List<SalesItem> getSalesItems() { return salesItems; }
    public void setSalesItems(List<SalesItem> salesItems) { this.salesItems = salesItems; }
}
