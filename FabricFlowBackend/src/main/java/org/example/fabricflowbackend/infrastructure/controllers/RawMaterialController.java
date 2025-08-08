package org.example.fabricflowbackend.infrastructure.controllers;


import org.example.fabricflowbackend.Domain.services.RawMaterialUseCase;
import org.example.fabricflowbackend.application.dto.RawMaterial.*;
import org.example.fabricflowbackend.application.RawMaterialService;
import org.example.fabricflowbackend.Domain.entities.RawMaterial;
import org.example.fabricflowbackend.Domain.exceptions.RawMaterialNotFoundException;
import org.example.fabricflowbackend.infrastructure.annotation.RoleAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/raw-materials")
public class RawMaterialController {

    private final RawMaterialUseCase rawMaterialService;

    public RawMaterialController(RawMaterialUseCase rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @PostMapping
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<RawMaterialResponseDto> createRawMaterial(@RequestBody RawMaterialRequestDto rawMaterialRequestDTO) {
        RawMaterial rawMaterial = convertToEntity(rawMaterialRequestDTO);
        RawMaterial createdMaterial = rawMaterialService.createRawMaterial(rawMaterial);
        return new ResponseEntity<>(convertToDTO(createdMaterial), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<RawMaterialResponseDto> updateRawMaterial(
            @PathVariable UUID id,
            @RequestBody RawMaterialRequestDto rawMaterialRequestDTO) {
        RawMaterial rawMaterial = convertToEntity(rawMaterialRequestDTO);
        rawMaterial.setId(id);
        RawMaterial updatedMaterial = rawMaterialService.updateRawMaterial(rawMaterial);
        return ResponseEntity.ok(convertToDTO(updatedMaterial));
    }

    @GetMapping("/{id}")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<RawMaterialResponseDto> getRawMaterialById(@PathVariable UUID id) {
        return rawMaterialService.getRawMaterialById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RawMaterialNotFoundException(id));
    }

    @GetMapping
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<List<RawMaterialResponseDto>> getAllRawMaterials() {
        List<RawMaterialResponseDto> materials = rawMaterialService.getAllRawMaterials().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(materials);
    }

    @GetMapping("/category/{category}")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<List<RawMaterialResponseDto>> getRawMaterialsByCategory(@PathVariable String category) {
        List<RawMaterialResponseDto> materials = rawMaterialService.getRawMaterialsByCategory(category).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(materials);
    }

    @DeleteMapping("/{id}")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<Void> deleteRawMaterial(@PathVariable UUID id) {
        rawMaterialService.deleteRawMaterial(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/consume")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<Void> consumeStock(
            @PathVariable UUID id,
            @RequestBody StockUpdateDto stockUpdateDTO) {
        rawMaterialService.consumeStock(id, stockUpdateDTO.getQuantity());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/add-stock")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<Void> addStock(
            @PathVariable UUID id,
            @RequestBody StockUpdateDto stockUpdateDTO) {
        rawMaterialService.addStock(id, stockUpdateDTO.getQuantity());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/needing-reorder")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<List<RawMaterialResponseDto>> getMaterialsNeedingReorder() {
        List<RawMaterialResponseDto> materials = rawMaterialService.getMaterialsNeedingReorder().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(materials);
    }

    @GetMapping("/low-stock")
    @RoleAllowed({ "ADMIN", "STORE_MANAGER"})
    public ResponseEntity<List<RawMaterialResponseDto>> getLowStockMaterials(
            @RequestParam(required = false, defaultValue = "0") int threshold) {
        List<RawMaterialResponseDto> materials = rawMaterialService.getLowStockMaterials(threshold).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(materials);
    }

    // Helper methods to convert between Entity and DTO
    private RawMaterial convertToEntity(RawMaterialRequestDto dto) {
        RawMaterial material = new RawMaterial();
        material.setName(dto.getName());
        material.setUnit(dto.getUnit());
        material.setCurrentStock(dto.getCurrentStock());
        material.setReorderLevel(dto.getReorderLevel());
        material.setCategory(dto.getCategory());
        return material;
    }

    private RawMaterialResponseDto convertToDTO(RawMaterial material) {
        RawMaterialResponseDto dto = new RawMaterialResponseDto();
        dto.setId(material.getId());
        dto.setName(material.getName());
        dto.setUnit(material.getUnit());
        dto.setCurrentStock(material.getCurrentStock());
        dto.setReorderLevel(material.getReorderLevel());
        dto.setCategory(material.getCategory());
        dto.setCreatedAt(material.getCreatedAt());
        return dto;
    }
}