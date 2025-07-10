package org.example.fabricflowbackend.Domain.services;

import org.example.fabricflowbackend.Domain.entities.Product;
import org.example.fabricflowbackend.Domain.entities.Variant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductUseCase {
    Product createProduct(Product product);
    Product updateProduct(Product product);
    Optional<Product> getProductById(UUID id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    void deleteProduct(UUID id);

    // Variant operations
    Variant createVariant(Variant variant);
    Variant updateVariant(Variant variant);
    Optional<Variant> getVariantById(UUID id);
    List<Variant> getVariantsByProductId(UUID productId);
    List<Variant> getLowStockVariants(int threshold);
    void deleteVariant(UUID id);

    // Business operations
    String generateSku(UUID productId, String size, String color);
    boolean isSkuUnique(String sku);
}
