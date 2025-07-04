package com.garments.inventory.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class SalesOrderDTO {
    private UUID id;
    private String customerName;
    private String status;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private List<SalesItemDTO> salesItems;

    // Constructors
    public SalesOrderDTO() {}

    public SalesOrderDTO(UUID id, String customerName, String status, LocalDate orderDate, BigDecimal totalAmount) {
        this.id = id;
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

    public List<SalesItemDTO> getSalesItems() { return salesItems; }
    public void setSalesItems(List<SalesItemDTO> salesItems) { this.salesItems = salesItems; }
}