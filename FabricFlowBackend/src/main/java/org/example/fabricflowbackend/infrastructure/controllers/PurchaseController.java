package org.example.fabricflowbackend.infrastructure.controllers;
import org.example.fabricflowbackend.Domain.services.PurchaseUseCase;
import org.example.fabricflowbackend.application.PurchaseService;
import org.example.fabricflowbackend.Domain.entities.Purchase;
import org.example.fabricflowbackend.Domain.entities.PurchaseItem;
import org.example.fabricflowbackend.Domain.exceptions.InvalidOperationException;
import org.example.fabricflowbackend.application.dto.purchase.PurchaseRequestDTO;
import org.example.fabricflowbackend.application.dto.purchase.PurchaseResponseDTO;
import org.example.fabricflowbackend.application.dto.purchase.StatusUpdateDTO;
import org.example.fabricflowbackend.application.dto.purchaseitem.PurchaseItemRequestDTO;
import org.example.fabricflowbackend.application.dto.purchaseitem.PurchaseItemResponseDTO;
import org.example.fabricflowbackend.infrastructure.annotation.RoleAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseUseCase purchaseService;

    public PurchaseController(PurchaseUseCase purchaseService) {
        this.purchaseService = purchaseService;
    }

    @PostMapping
    @RoleAllowed("ADMIN")
    public ResponseEntity<PurchaseResponseDTO> createPurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        Purchase purchase = convertToEntity(purchaseRequestDTO);
        Purchase createdPurchase = purchaseService.createPurchase(purchase);
        return new ResponseEntity<>(convertToDTO(createdPurchase), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<PurchaseResponseDTO> updatePurchase(
            @PathVariable UUID id,
            @RequestBody PurchaseRequestDTO purchaseRequestDTO) {
        Purchase purchase = convertToEntity(purchaseRequestDTO);
        purchase.setId(id);
        Purchase updatedPurchase = purchaseService.updatePurchase(purchase);
        return ResponseEntity.ok(convertToDTO(updatedPurchase));
    }

    @GetMapping("/{id}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<PurchaseResponseDTO> getPurchaseById(@PathVariable UUID id) {
        return purchaseService.getPurchaseById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new InvalidOperationException("Purchase not found with id: " + id));
    }

    @GetMapping
    @RoleAllowed("ADMIN")
    public ResponseEntity<List<PurchaseResponseDTO>> getAllPurchases() {
        List<PurchaseResponseDTO> purchases = purchaseService.getAllPurchases().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/supplier/{supplierId}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<List<PurchaseResponseDTO>> getPurchasesBySupplier(@PathVariable UUID supplierId) {
        List<PurchaseResponseDTO> purchases = purchaseService.getPurchasesBySupplier(supplierId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/status/{status}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<List<PurchaseResponseDTO>> getPurchasesByStatus(@PathVariable String status) {
        List<PurchaseResponseDTO> purchases = purchaseService.getPurchasesByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/date-range")
    @RoleAllowed("ADMIN")
    public ResponseEntity<List<PurchaseResponseDTO>> getPurchasesByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<PurchaseResponseDTO> purchases = purchaseService.getPurchasesByDateRange(startDate, endDate).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(purchases);
    }

    @DeleteMapping("/{id}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<Void> deletePurchase(@PathVariable UUID id) {
        purchaseService.deletePurchase(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{purchaseId}/items")
    @RoleAllowed("ADMIN")
    public ResponseEntity<PurchaseItemResponseDTO> addPurchaseItem(
            @PathVariable UUID purchaseId,
            @RequestBody PurchaseItemRequestDTO purchaseItemRequestDTO) {
        PurchaseItem item = convertToEntity(purchaseItemRequestDTO);
        PurchaseItem createdItem = purchaseService.addPurchaseItem(purchaseId, item);
        return new ResponseEntity<>(convertToDTO(createdItem), HttpStatus.CREATED);
    }

    @DeleteMapping("/items/{itemId}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<Void> removePurchaseItem(@PathVariable UUID itemId) {
        purchaseService.removePurchaseItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{purchaseId}/items")
    @RoleAllowed("ADMIN")
    public ResponseEntity<List<PurchaseItemResponseDTO>> getPurchaseItems(@PathVariable UUID purchaseId) {
        List<PurchaseItemResponseDTO> items = purchaseService.getPurchaseItems(purchaseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(items);
    }

    @PostMapping("/{purchaseId}/receive")
    @RoleAllowed("ADMIN")
    public ResponseEntity<Void> markPurchaseAsReceived(@PathVariable UUID purchaseId) {
        purchaseService.markPurchaseAsReceived(purchaseId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{purchaseId}/process-receipt")
    @RoleAllowed("ADMIN")
    public ResponseEntity<Void> processPurchaseReceipt(@PathVariable UUID purchaseId) {
        purchaseService.processPurchaseReceipt(purchaseId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{purchaseId}/status")
    @RoleAllowed("ADMIN")
    public ResponseEntity<Void> updatePurchaseStatus(
            @PathVariable UUID purchaseId,
            @RequestBody StatusUpdateDTO statusUpdateDTO) {
        if ("RECEIVED".equals(statusUpdateDTO.getStatus())) {
            purchaseService.processPurchaseReceipt(purchaseId);
        } else if ("CANCELLED".equals(statusUpdateDTO.getStatus())) {
            purchaseService.getPurchaseById(purchaseId)
                    .ifPresent(purchase -> {
                        purchase.markAsCancelled();
                        purchaseService.updatePurchase(purchase);
                    });
        }
        return ResponseEntity.ok().build();
    }

    // Helper methods to convert between Entity and DTO
    private Purchase convertToEntity(PurchaseRequestDTO dto) {
        Purchase purchase = new Purchase(dto.getSupplierId(), dto.getOrderDate());
        if (dto.getItems() != null) {
            List<PurchaseItem> items = dto.getItems().stream()
                    .map(this::convertToEntity)
                    .collect(Collectors.toList());
            purchase.setItems(items);
        }
        return purchase;
    }

    private PurchaseItem convertToEntity(PurchaseItemRequestDTO dto) {
        return new PurchaseItem(null, dto.getMaterialId(), dto.getQuantity(), dto.getUnitPrice());
    }

    private PurchaseResponseDTO convertToDTO(Purchase purchase) {
        PurchaseResponseDTO dto = new PurchaseResponseDTO();
        dto.setId(purchase.getId());
        dto.setSupplierId(purchase.getSupplierId());
        dto.setOrderDate(purchase.getOrderDate());
        dto.setStatus(purchase.getStatus());
        dto.setTotalAmount(purchase.getTotalAmount());
        dto.setCreatedAt(purchase.getCreatedAt());

        if (purchase.getItems() != null) {
            List<PurchaseItemResponseDTO> itemDTOs = purchase.getItems().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            dto.setItems(itemDTOs);
        }

        return dto;
    }

    private PurchaseItemResponseDTO convertToDTO(PurchaseItem item) {
        PurchaseItemResponseDTO dto = new PurchaseItemResponseDTO();
        dto.setId(item.getId());
        dto.setPurchaseId(item.getPurchaseId());
        dto.setMaterialId(item.getMaterialId());
        dto.setQuantity(item.getQuantity());
        dto.setUnitPrice(item.getUnitPrice());
        dto.setTotalPrice(item.getTotalPrice());
        return dto;
    }
}