package org.example.fabricflowbackend.infrastructure.controllers;


import org.example.fabricflowbackend.application.SalesService;
import org.example.fabricflowbackend.Domain.entities.SalesItem;
import org.example.fabricflowbackend.Domain.entities.SalesOrder;
import org.example.fabricflowbackend.Domain.exceptions.VariantNotFoundException;
import org.example.fabricflowbackend.application.dto.salesitem.SalesItemRequestDTO;
import org.example.fabricflowbackend.application.dto.salesitem.SalesItemResponseDTO;
import org.example.fabricflowbackend.application.dto.salesorder.SalesOrderRequestDTO;
import org.example.fabricflowbackend.application.dto.salesorder.SalesOrderResponseDTO;
import org.example.fabricflowbackend.application.dto.salesorder.StatusUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PostMapping("/orders")
    public ResponseEntity<SalesOrderResponseDTO> createSalesOrder(@RequestBody SalesOrderRequestDTO salesOrderRequestDTO) {
        SalesOrder salesOrder = convertToEntity(salesOrderRequestDTO);
        SalesOrder createdOrder = salesService.createSalesOrder(salesOrder);
        return new ResponseEntity<>(convertToDTO(createdOrder), HttpStatus.CREATED);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<SalesOrderResponseDTO> updateSalesOrder(
            @PathVariable UUID id,
            @RequestBody SalesOrderRequestDTO salesOrderRequestDTO) {
        SalesOrder salesOrder = convertToEntity(salesOrderRequestDTO);
        salesOrder.setId(id);
        SalesOrder updatedOrder = salesService.updateSalesOrder(salesOrder);
        return ResponseEntity.ok(convertToDTO(updatedOrder));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<SalesOrderResponseDTO> getSalesOrderById(@PathVariable UUID id) {
        return salesService.getSalesOrderById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new IllegalArgumentException("Sales order not found"));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<SalesOrderResponseDTO>> getAllSalesOrders() {
        List<SalesOrderResponseDTO> orders = salesService.getAllSalesOrders().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/customer/{customerName}")
    public ResponseEntity<List<SalesOrderResponseDTO>> getSalesOrdersByCustomer(@PathVariable String customerName) {
        List<SalesOrderResponseDTO> orders = salesService.getSalesOrdersByCustomer(customerName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/status/{status}")
    public ResponseEntity<List<SalesOrderResponseDTO>> getSalesOrdersByStatus(@PathVariable String status) {
        List<SalesOrderResponseDTO> orders = salesService.getSalesOrdersByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/orders/date-range")
    public ResponseEntity<List<SalesOrderResponseDTO>> getSalesOrdersByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<SalesOrderResponseDTO> orders = salesService.getSalesOrdersByDateRange(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteSalesOrder(@PathVariable UUID id) {
        salesService.deleteSalesOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/orders/{orderId}/items")
    public ResponseEntity<SalesItemResponseDTO> addSalesItem(
            @PathVariable UUID orderId,
            @RequestBody SalesItemRequestDTO salesItemRequestDTO) {
        SalesItem item = convertToEntity(salesItemRequestDTO);
        SalesItem createdItem = salesService.addSalesItem(orderId, item);
        return new ResponseEntity<>(convertToDTO(createdItem), HttpStatus.CREATED);
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeSalesItem(@PathVariable UUID itemId) {
        salesService.removeSalesItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/orders/{orderId}/items")
    public ResponseEntity<List<SalesItemResponseDTO>> getSalesItems(@PathVariable UUID orderId) {
        List<SalesItemResponseDTO> items = salesService.getSalesItems(orderId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @PostMapping("/orders/{orderId}/process")
    public ResponseEntity<Void> processSalesOrder(@PathVariable UUID orderId) {
        salesService.processSalesOrder(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/{orderId}/deliver")
    public ResponseEntity<Void> markSalesOrderAsDelivered(@PathVariable UUID orderId) {
        salesService.markSalesOrderAsDelivered(orderId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestBody StatusUpdateDTO statusUpdateDTO) {
        if ("DELIVERED".equals(statusUpdateDTO.getStatus())) {
            salesService.markSalesOrderAsDelivered(orderId);
        } else if ("CANCELLED".equals(statusUpdateDTO.getStatus())) {
            // Assuming you'll add a cancel method to the service
            salesService.getSalesOrderById(orderId)
                    .ifPresent(order -> {
                        order.markAsCancelled();
                        salesService.updateSalesOrder(order);
                    });
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/stock/check")
    public ResponseEntity<Boolean> checkStockAvailability(
            @RequestParam UUID variantId,
            @RequestParam int quantity) {
        try {
            boolean available = salesService.checkStockAvailability(variantId, quantity);
            return ResponseEntity.ok(available);
        } catch (VariantNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Helper methods to convert between Entity and DTO
    private SalesOrder convertToEntity(SalesOrderRequestDTO dto) {
        SalesOrder order = new SalesOrder(dto.getCustomerName(), dto.getOrderDate());
        if (dto.getItems() != null) {
            List<SalesItem> items = dto.getItems().stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());
            order.setItems(items);
        }
        return order;
    }

    private SalesItem convertToEntity(SalesItemRequestDTO dto) {
        return new SalesItem(null, dto.getVariantId(), dto.getQuantity(), dto.getUnitPrice());
    }

    private SalesOrderResponseDTO convertToDTO(SalesOrder order) {
        SalesOrderResponseDTO dto = new SalesOrderResponseDTO();
        dto.setId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setStatus(order.getStatus());
        dto.setOrderDate(order.getOrderDate());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCreatedAt(order.getCreatedAt());

        if (order.getItems() != null) {
            List<SalesItemResponseDTO> itemDTOs = order.getItems().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            dto.setItems(itemDTOs);
        }

        return dto;
    }

    private SalesItemResponseDTO convertToDTO(SalesItem item) {
        SalesItemResponseDTO dto = new SalesItemResponseDTO();
        dto.setId(item.getId());
        dto.setSalesOrderId(item.getSalesOrderId());
        dto.setVariantId(item.getVariantId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        return dto;
    }
}