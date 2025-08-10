package org.example.fabricflowbackend.infrastructure.controllers;


import org.example.fabricflowbackend.Domain.services.StockUseCase;
import org.example.fabricflowbackend.application.StockService;
import org.example.fabricflowbackend.Domain.entities.RawMaterial;
import org.example.fabricflowbackend.Domain.entities.Variant;
import org.example.fabricflowbackend.Domain.entities.StockAdjustment;
import org.example.fabricflowbackend.Domain.exceptions.InsufficientStockException;
import org.example.fabricflowbackend.Domain.exceptions.RawMaterialNotFoundException;
import org.example.fabricflowbackend.Domain.exceptions.VariantNotFoundException;
import org.example.fabricflowbackend.application.dto.stock.StockAdjustmentRequestDTO;
import org.example.fabricflowbackend.application.dto.stock.StockAdjustmentResponseDTO;
import org.example.fabricflowbackend.application.dto.stock.StockAlertDTO;
import org.example.fabricflowbackend.application.dto.stock.StockTransferRequestDTO;
import org.example.fabricflowbackend.application.dto.stock.StockValidationRequestDTO;
import org.example.fabricflowbackend.infrastructure.annotation.RoleAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockUseCase stockService;

    public StockController(StockUseCase stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/adjust")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<Void> adjustStock(@RequestBody StockAdjustmentRequestDTO adjustmentRequest) {
        if (adjustmentRequest.isRawMaterial()) {
            stockService.adjustRawMaterialStock(
                    adjustmentRequest.getItemId(),
                    adjustmentRequest.getQuantity(),
                    adjustmentRequest.getReason()
            );
        } else {
            stockService.adjustVariantStock(
                    adjustmentRequest.getItemId(),
                    adjustmentRequest.getQuantity(),
                    adjustmentRequest.getReason()
            );
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/transfer")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<Void> transferStock(@RequestBody StockTransferRequestDTO transferRequest) {
        if (transferRequest.isRawMaterial()) {
            stockService.transferRawMaterialStock(
                    transferRequest.getFromId(),
                    transferRequest.getToId(),
                    transferRequest.getQuantity()
            );
        } else {
            stockService.transferVariantStock(
                    transferRequest.getFromId(),
                    transferRequest.getToId(),
                    transferRequest.getQuantity()
            );
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/raw-materials/low-stock")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<List<RawMaterial>> getRawMaterialsWithLowStock() {
        List<RawMaterial> materials = stockService.getRawMaterialsWithLowStock();
        return ResponseEntity.ok(materials);
    }

    @GetMapping("/variants/low-stock")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<List<Variant>> getVariantsWithLowStock() {
        List<Variant> variants = stockService.getVariantsWithLowStock();
        return ResponseEntity.ok(variants);
    }

    @PostMapping("/validate")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<Boolean> validateStock(@RequestBody StockValidationRequestDTO validationRequest) {
        boolean isValid;
        if (validationRequest.isRawMaterial()) {
            isValid = stockService.validateRawMaterialStock(
                    validationRequest.getItemId(),
                    validationRequest.getRequiredQuantity()
            );
        } else {
            isValid = stockService.validateVariantStock(
                    validationRequest.getItemId(),
                    validationRequest.getRequiredQuantity()
            );
        }
        return ResponseEntity.ok(isValid);
    }

    @GetMapping("/alerts")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<List<StockAlertDTO>> getStockAlerts() {
        List<String> alerts = stockService.getStockAlerts();
        List<StockAlertDTO> alertDTOs = alerts.stream()
                .map(alert -> {
                    StockAlertDTO dto = new StockAlertDTO();
                    dto.setMessage(alert);
                    // Parse the alert message to extract details
                    if (alert.contains("Raw Material")) {
                        dto.setItemType("RAW_MATERIAL");
                    } else {
                        dto.setItemType("VARIANT");
                    }
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(alertDTOs);
    }

    @GetMapping("/adjustments")
    public ResponseEntity<List<StockAdjustmentResponseDTO>> getStockAdjustments() {
        List<StockAdjustment> adjustments = stockService.getAllStockAdjustments();
        List<StockAdjustmentResponseDTO> adjustmentDTOs = adjustments.stream()
                .map(adjustment -> {
                    StockAdjustmentResponseDTO dto = new StockAdjustmentResponseDTO();
                    dto.setId(adjustment.getId());
                    dto.setItemId(adjustment.getItemId());
                    dto.setItemType(adjustment.getItemType());
                    dto.setQuantityBefore(adjustment.getQuantityBefore());
                    dto.setQuantityAfter(adjustment.getQuantityAfter());
                    dto.setAdjustmentAmount(adjustment.getAdjustmentAmount());
                    dto.setReason(adjustment.getReason());
                    dto.setAdjustedBy(adjustment.getAdjustedBy());
                    dto.setAdjustmentDate(adjustment.getAdjustmentDate());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(adjustmentDTOs);
    }

    @GetMapping("/report")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<Void> generateStockReport() {
        stockService.generateStockReport();
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler({RawMaterialNotFoundException.class, VariantNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> handleInsufficientStockException(InsufficientStockException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}