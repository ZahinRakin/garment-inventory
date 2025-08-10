package org.example.fabricflowbackend.infrastructure.controllers;



import org.example.fabricflowbackend.Domain.entities.Product;
import org.example.fabricflowbackend.Domain.entities.Variant;
import org.example.fabricflowbackend.Domain.exceptions.*;
import org.example.fabricflowbackend.Domain.services.ProductUseCase;
import org.example.fabricflowbackend.application.dto.product.ProductRequestDto;
import org.example.fabricflowbackend.application.dto.product.ProductResponseDto;
import org.example.fabricflowbackend.application.dto.variant.VariantRequestDto;
import org.example.fabricflowbackend.application.dto.variant.VariantResponseDto;
import org.example.fabricflowbackend.infrastructure.annotation.RoleAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductUseCase productService;
    public ProductController(ProductUseCase productService) {
        this.productService = productService;
    }

    // Product Endpoints

    @PostMapping
    @RoleAllowed("ADMIN")
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setDescription(productDTO.getDescription());

        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(convertToProductResponseDTO(createdProduct), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @RoleAllowed({"ADMIN","SALES_OFFICER"})
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable UUID id,
            @RequestBody ProductRequestDto productDTO) {
        Product product = new Product();
        product.setId(id);
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setDescription(productDTO.getDescription());

        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.ok(convertToProductResponseDTO(updatedProduct));
    }

    @GetMapping("/{id}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        return productService.getProductById(id)
                .map(this::convertToProductResponseDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @GetMapping
    @RoleAllowed("ADMIN")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts().stream()
                .map(this::convertToProductResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{category}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(@PathVariable String category) {
        List<ProductResponseDto> products = productService.getProductsByCategory(category).stream()
                .map(this::convertToProductResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // Variant Endpoints

    @PostMapping("/{productId}/variants")
    @RoleAllowed("ADMIN")
    public ResponseEntity<VariantResponseDto> createVariant(
            @PathVariable UUID productId,
            @RequestBody VariantRequestDto variantDTO) {
        Variant variant = new Variant();
        variant.setProductId(productId);
        variant.setSize(variantDTO.getSize());
        variant.setColor(variantDTO.getColor());
        variant.setFabric(variantDTO.getFabric());
        variant.setQuantity(variantDTO.getQuantity());
        variant.setSku(variantDTO.getSku());

        Variant createdVariant = productService.createVariant(variant);
        return new ResponseEntity<>(convertToVariantResponseDTO(createdVariant), HttpStatus.CREATED);
    }

    @PutMapping("/variants/{id}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<VariantResponseDto> updateVariant(
            @PathVariable UUID id,
            @RequestBody VariantRequestDto variantDTO) {
        Variant variant = new Variant();
        variant.setId(id);
        variant.setProductId(variantDTO.getProductId());
        variant.setSize(variantDTO.getSize());
        variant.setColor(variantDTO.getColor());
        variant.setFabric(variantDTO.getFabric());
        variant.setQuantity(variantDTO.getQuantity());
        variant.setSku(variantDTO.getSku());

        Variant updatedVariant = productService.updateVariant(variant);
        return ResponseEntity.ok(convertToVariantResponseDTO(updatedVariant));
    }

    @GetMapping("/variants/{id}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<VariantResponseDto> getVariantById(@PathVariable UUID id) {
        return productService.getVariantById(id)
                .map(this::convertToVariantResponseDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new VariantNotFoundException(id));
    }

    @GetMapping("/{productId}/variants")
    @RoleAllowed("ADMIN")
    public ResponseEntity<List<VariantResponseDto>> getVariantsByProductId(@PathVariable UUID productId) {
        List<VariantResponseDto> variants = productService.getVariantsByProductId(productId).stream()
                .map(this::convertToVariantResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(variants);
    }

    @GetMapping("/variants/low-stock")
    @RoleAllowed("ADMIN")
    public ResponseEntity<List<VariantResponseDto>> getLowStockVariants(
            @RequestParam(defaultValue = "10") int threshold) {
        List<VariantResponseDto> variants = productService.getLowStockVariants(threshold).stream()
                .map(this::convertToVariantResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(variants);
    }

    @DeleteMapping("/variants/{id}")
    @RoleAllowed("ADMIN")
    public ResponseEntity<Void> deleteVariant(@PathVariable UUID id) {
        productService.deleteVariant(id);
        return ResponseEntity.noContent().build();
    }

    // Exception Handling

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(VariantNotFoundException.class)
    public ResponseEntity<String> handleVariantNotFoundException(VariantNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DuplicateSkuException.class)
    public ResponseEntity<String> handleDuplicateSkuException(DuplicateSkuException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleIllegalStateException(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    // Helper methods for conversion between entities and DTOs

    private ProductResponseDto convertToProductResponseDTO(Product product) {
        ProductResponseDto dto = new ProductResponseDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setDescription(product.getDescription());
        return dto;
    }

    private VariantResponseDto convertToVariantResponseDTO(Variant variant) {
        VariantResponseDto dto = new VariantResponseDto();
        dto.setId(variant.getId());
        dto.setProductId(variant.getProductId());
        dto.setSize(variant.getSize());
        dto.setColor(variant.getColor());
        dto.setFabric(variant.getFabric());
        dto.setQuantity(variant.getQuantity());
        dto.setSku(variant.getSku());
        dto.setCreatedAt(variant.getCreatedAt());
        return dto;
    }
}
