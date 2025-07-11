package org.example.fabricflowbackend.application;

import org.example.fabricflowbackend.Domain.entities.Product;
import org.example.fabricflowbackend.Domain.entities.Variant;
import org.example.fabricflowbackend.Domain.exceptions.DuplicateSkuException;
import org.example.fabricflowbackend.Domain.exceptions.ProductNotFoundException;
import org.example.fabricflowbackend.Domain.exceptions.VariantNotFoundException;
import org.example.fabricflowbackend.Domain.repositories.ProductRepository;
import org.example.fabricflowbackend.Domain.repositories.VariantRepository;
import org.example.fabricflowbackend.Domain.services.ProductUseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service

public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;
    private final VariantRepository variantRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, VariantRepository variantRepository) {
        this.productRepository = productRepository;
        this.variantRepository = variantRepository;
    }

    @Override
    public Product createProduct(Product product) {
        System.out.println("create product");
        System.out.println(product+"hello");
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }

        Optional<Product> existingProduct = productRepository.findByName(product.getName());
        if (existingProduct.isPresent()) {
            throw new IllegalArgumentException("Product with name '" + product.getName() + "' already exists");
        }
        System.out.println(product);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        if (!productRepository.existsById(product.getId())) {
            throw new ProductNotFoundException(product.getId());
        }
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }

        // Check if product has variants
        List<Variant> variants = variantRepository.findByProductId(id);
        if (!variants.isEmpty()) {
            throw new IllegalStateException("Cannot delete product with existing variants");
        }

        productRepository.deleteById(id);
    }

    @Override
    public Variant createVariant(Variant variant) {
        if (!productRepository.existsById(variant.getProductId())) {
            throw new ProductNotFoundException(variant.getProductId());
        }

        if (variant.getSku() != null && !isSkuUnique(variant.getSku())) {
            throw new DuplicateSkuException(variant.getSku());
        }

        if (variant.getSku() == null || variant.getSku().trim().isEmpty()) {
            variant.setSku(generateSku(variant.getProductId(), variant.getSize(), variant.getColor()));
        }

        return variantRepository.save(variant);
    }

    @Override
    public Variant updateVariant(Variant variant) {
        if (!variantRepository.existsById(variant.getId())) {
            throw new VariantNotFoundException(variant.getId());
        }

        // Check SKU uniqueness if changed
        Optional<Variant> existingVariant = variantRepository.findById(variant.getId());
        if (existingVariant.isPresent() && !existingVariant.get().getSku().equals(variant.getSku())) {
            if (!isSkuUnique(variant.getSku())) {
                throw new DuplicateSkuException(variant.getSku());
            }
        }

        return variantRepository.save(variant);
    }

    @Override
    public Optional<Variant> getVariantById(UUID id) {
        return variantRepository.findById(id);
    }

    @Override
    public List<Variant> getVariantsByProductId(UUID productId) {
        return variantRepository.findByProductId(productId);
    }

    @Override
    public List<Variant> getLowStockVariants(int threshold) {
        return variantRepository.findByQuantityLessThanEqual(threshold);
    }

    @Override
    public void deleteVariant(UUID id) {
        if (!variantRepository.existsById(id)) {
            throw new VariantNotFoundException(id);
        }
        variantRepository.deleteById(id);
    }

    @Override
    public String generateSku(UUID productId, String size, String color) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(productId);
        }

        String productCode = product.get().getName().substring(0, Math.min(3, product.get().getName().length())).toUpperCase();
        String sizeCode = size != null ? size.toUpperCase() : "NA";
        String colorCode = color != null ? color.substring(0, Math.min(3, color.length())).toUpperCase() : "NA";

        return String.format("%s-%s-%s-%s", productCode, sizeCode, colorCode, System.currentTimeMillis() % 10000);
    }

    @Override
    public boolean isSkuUnique(String sku) {
        return variantRepository.findBySku(sku).isEmpty();
    }
}
