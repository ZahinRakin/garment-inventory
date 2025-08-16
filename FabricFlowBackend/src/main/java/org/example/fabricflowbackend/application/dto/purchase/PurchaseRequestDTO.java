package org.example.fabricflowbackend.application.dto.purchase;



import org.example.fabricflowbackend.application.dto.purchaseitem.PurchaseItemRequestDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class PurchaseRequestDTO {
    private UUID supplierId;
    private LocalDate orderDate;
    private List<PurchaseItemRequestDTO> items;

    // Getters and Setters
    public UUID getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(UUID supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    

    public void setItems(List<PurchaseItemRequestDTO> items) {
        this.items = items;
    }

    public List<PurchaseItemRequestDTO> getItems() {
        return items;
    }
}