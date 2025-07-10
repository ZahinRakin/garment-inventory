package org.example.fabricflowbackend.application.dto.salesorder;


import org.example.fabricflowbackend.application.dto.salesitem.SalesItemRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SalesOrderRequestDTO {
    private String customerName;
    private LocalDate orderDate;
    private List<SalesItemRequestDTO> items;

    // Getters and Setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<SalesItemRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<SalesItemRequestDTO> items) {
        this.items = items;
    }
}