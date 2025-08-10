package org.example.fabricflowbackend.infrastructure.controllers;


import org.example.fabricflowbackend.Domain.services.SupplierUseCase;
import org.example.fabricflowbackend.application.SupplierService;
import org.example.fabricflowbackend.Domain.entities.Supplier;
import org.example.fabricflowbackend.Domain.exceptions.SupplierNotFoundException;
import org.example.fabricflowbackend.application.dto.supplier.SupplierRequestDto;
import org.example.fabricflowbackend.application.dto.supplier.SupplierResponseDto;
import org.example.fabricflowbackend.infrastructure.annotation.RoleAllowed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierUseCase supplierService;

    public SupplierController(SupplierUseCase supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    @RoleAllowed("ADMIN")
    public ResponseEntity<SupplierResponseDto> createSupplier(@RequestBody SupplierRequestDto supplierRequestDTO) {
        Supplier supplier = convertToEntity(supplierRequestDTO);
        Supplier createdSupplier = supplierService.createSupplier(supplier);
        return new ResponseEntity<>(convertToDTO(createdSupplier), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<SupplierResponseDto> updateSupplier(
            @PathVariable UUID id,
            @RequestBody SupplierRequestDto supplierRequestDTO) {
        Supplier supplier = convertToEntity(supplierRequestDTO);
        supplier.setId(id);
        Supplier updatedSupplier = supplierService.updateSupplier(supplier);
        return ResponseEntity.ok(convertToDTO(updatedSupplier));
    }

    @GetMapping("/{id}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<SupplierResponseDto> getSupplierById(@PathVariable UUID id) {
        return supplierService.getSupplierById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new SupplierNotFoundException(id));
    }

    @GetMapping
    @RoleAllowed("ADMIN")
    public ResponseEntity<List<SupplierResponseDto>> getAllSuppliers() {
        List<SupplierResponseDto> suppliers = supplierService.getAllSuppliers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/search")
    @RoleAllowed("ADMIN")
    public ResponseEntity<List<SupplierResponseDto>> findSuppliersByName(@RequestParam String name) {
        List<SupplierResponseDto> suppliers = supplierService.findSuppliersByName(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(suppliers);
    }

    @DeleteMapping("/{id}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<Void> deleteSupplier(@PathVariable UUID id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    // Helper methods to convert between Entity and DTO
    private Supplier convertToEntity(SupplierRequestDto dto) {
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        supplier.setAddress(dto.getAddress());
        return supplier;
    }

    private SupplierResponseDto convertToDTO(Supplier supplier) {
        SupplierResponseDto dto = new SupplierResponseDto();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        dto.setEmail(supplier.getEmail());
        dto.setPhone(supplier.getPhone());
        dto.setAddress(supplier.getAddress());
        return dto;
    }
}