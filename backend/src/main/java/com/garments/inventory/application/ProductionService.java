package com.garments.inventory.application.services;

import com.garments.inventory.application.dto.ProductDTO;
import com.garments.inventory.application.dto.VariantDTO;
import com.garments.inventory.domain.entities.Product;
import com.garments.inventory.domain.entities.Variant;
import com.garments.inventory.domain.repositories.ProductRepository;
import com.garments.inventory.domain.repositories.VariantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VariantRepository variantRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product(productDTO.getName(), productDTO.getCategory(), productDTO.getDescription());
        Product savedProduct = productRepository.save(product);
        return mapToDTO(savedProduct);
    }

    public Optional<ProductDTO> getProductById(UUID id) {
        return productRepository.findById(id).map(this::mapToDTO);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategory(String category) {
        return productRepository.findByCategory(category).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO updateProduct(UUID id, ProductDTO productDTO) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(productDTO.getName());
            product.setCategory(productDTO.getCategory());
            product.setDescription(productDTO.getDescription());
            Product savedProduct = productRepository.save(product);
            return mapToDTO(savedProduct);
        }
        throw new RuntimeException("Product not found with id: " + id);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    public VariantDTO createVariant(VariantDTO variantDTO) {
        Variant variant = new Variant(variantDTO.getProductId(), variantDTO.getSize(),
                variantDTO.getColor(), variantDTO.getFabric(),
                variantDTO.getQuantity(), variantDTO.getSku());
        Variant savedVariant = variantRepository.save(variant);
        return mapVariantToDTO(savedVariant);
    }

    public List<VariantDTO> getVariantsByProductId(UUID productId) {
        return variantRepository.findByProductId(productId).stream()
                .map(this::mapVariantToDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO(product.getId(), product.getName(),
                product.getCategory(), product.getDescription(),
                product.getCreatedAt());
        if (product.getVariants() != null) {
            dto.setVariants(product.getVariants().stream()
                    .map(this::mapVariantToDTO)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    private VariantDTO mapVariantToDTO(Variant variant) {
        return new VariantDTO(variant.getId(), variant.getProductId(), variant.getSize(),
                variant.getColor(), variant.getFabric(), variant.getQuantity(),
                variant.getSku(), variant.getCreatedAt());
    }
}