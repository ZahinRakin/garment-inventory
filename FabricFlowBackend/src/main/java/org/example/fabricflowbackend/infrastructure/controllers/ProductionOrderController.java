package org.example.fabricflowbackend.infrastructure.controllers;

import org.example.fabricflowbackend.Domain.entities.ProductionOrder;
import org.example.fabricflowbackend.Domain.exceptions.InvalidOperationException;
import org.example.fabricflowbackend.Domain.exceptions.VariantNotFoundException;
import org.example.fabricflowbackend.Domain.services.ProductionUseCase;
import org.example.fabricflowbackend.application.dto.productionorder.ProductionOrderRequestDto;
import org.example.fabricflowbackend.application.dto.productionorder.ProductionOrderResponseDto;
import org.example.fabricflowbackend.infrastructure.annotation.RoleAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/production-orders")
public class ProductionOrderController {

    private final ProductionUseCase productionService;

    public ProductionOrderController(ProductionUseCase productionService) {
        this.productionService = productionService;
    }

    @PostMapping
    @RoleAllowed({ "ADMIN", "PRODUCTION_OFFICER"})
    public ResponseEntity<ProductionOrderResponseDto> createProductionOrder(
            @RequestBody ProductionOrderRequestDto requestDTO) {
        ProductionOrder order = new ProductionOrder();
        order.setVariantId(requestDTO.getVariantId());
        order.setQuantity(requestDTO.getQuantity());
        order.setStartDate(requestDTO.getStartDate());
        order.setEndDate(requestDTO.getEndDate());

        ProductionOrder createdOrder = productionService.createProductionOrder(order);
        return new ResponseEntity<>(convertToResponseDto(createdOrder), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RoleAllowed({ "ADMIN", "PRODUCTION_OFFICER"})
    public ResponseEntity<ProductionOrderResponseDto> updateProductionOrder(
            @PathVariable UUID id,
            @RequestBody ProductionOrderRequestDto requestDTO) {
        ProductionOrder order = new ProductionOrder();
        order.setId(id);
        order.setVariantId(requestDTO.getVariantId());
        order.setQuantity(requestDTO.getQuantity());

        ProductionOrder updatedOrder = productionService.updateProductionOrder(order);
        return ResponseEntity.ok(convertToResponseDto(updatedOrder));
    }

    @GetMapping("/{id}")
    @RoleAllowed({ "ADMIN", "PRODUCTION_OFFICER"})
    public ResponseEntity<ProductionOrderResponseDto> getProductionOrderById(@PathVariable UUID id) {
        return productionService.getProductionOrderById(id)
                .map(this::convertToResponseDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new InvalidOperationException("Production order not found"));
    }

    @GetMapping
    @RoleAllowed({ "ADMIN", "PRODUCTION_OFFICER"})
    public ResponseEntity<List<ProductionOrderResponseDto>> getAllProductionOrders() {
        List<ProductionOrderResponseDto> orders = productionService.getAllProductionOrders().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/variant/{variantId}")
    @RoleAllowed({ "ADMIN", "PRODUCTION_OFFICER"})
    public ResponseEntity<List<ProductionOrderResponseDto>> getProductionOrdersByVariant(
            @PathVariable UUID variantId) {
        List<ProductionOrderResponseDto> orders = productionService.getProductionOrdersByVariant(variantId).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    @RoleAllowed({ "ADMIN", "PRODUCTION_OFFICER"})
    public ResponseEntity<List<ProductionOrderResponseDto>> getProductionOrdersByStatus(
            @PathVariable String status) {
        List<ProductionOrderResponseDto> orders = productionService.getProductionOrdersByStatus(status).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    @RoleAllowed({ "ADMIN", "PRODUCTION_OFFICER"})
    public ResponseEntity<Void> deleteProductionOrder(@PathVariable UUID id) {
        productionService.deleteProductionOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/start")
    @RoleAllowed({ "ADMIN", "PRODUCTION_OFFICER"})
    public ResponseEntity<ProductionOrderResponseDto> startProduction(@PathVariable UUID id) {
        productionService.startProduction(id);
        return productionService.getProductionOrderById(id)
                .map(this::convertToResponseDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new InvalidOperationException("Production order not found"));
    }

    @PostMapping("/{id}/complete")
    @RoleAllowed({ "ADMIN", "PRODUCTION_OFFICER"})
    public ResponseEntity<ProductionOrderResponseDto> completeProduction(@PathVariable UUID id) {
        productionService.completeProduction(id);
        return productionService.getProductionOrderById(id)
                .map(this::convertToResponseDto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new InvalidOperationException("Production order not found"));
    }

    @GetMapping("/pending")
    @RoleAllowed({ "ADMIN", "PRODUCTION_OFFICER"})
    public ResponseEntity<List<ProductionOrderResponseDto>> getPendingOrders() {
        List<ProductionOrderResponseDto> orders = productionService.getPendingOrders().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/in-progress")
    @RoleAllowed({ "ADMIN", "PRODUCTION_OFFICER"})
    public ResponseEntity<List<ProductionOrderResponseDto>> getInProgressOrders() {
        List<ProductionOrderResponseDto> orders = productionService.getInProgressOrders().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }

    // Exception handling
    @ExceptionHandler(InvalidOperationException.class)
    public ResponseEntity<String> handleInvalidOperationException(InvalidOperationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(VariantNotFoundException.class)
    public ResponseEntity<String> handleVariantNotFoundException(VariantNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Helper method to convert entity to DTO
    private ProductionOrderResponseDto convertToResponseDto(ProductionOrder order) {
        ProductionOrderResponseDto dto = new ProductionOrderResponseDto();
        dto.setId(order.getId());
        dto.setVariantId(order.getVariantId());
        dto.setQuantity(order.getQuantity());
        dto.setStatus(order.getStatus());
        dto.setStartDate(order.getStartDate());
        dto.setEndDate(order.getEndDate());
        dto.setCreatedAt(order.getCreatedAt());
        return dto;
    }
}