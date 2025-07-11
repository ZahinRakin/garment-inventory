package org.example.fabricflowbackend.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.Product;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariantEntity> variants;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Product toDomain() {
        Product product = new Product(name, category, description);
        product.setId(id);
        product.setCreatedAt(createdAt);
        // Note: Variants would need to be converted separately
        return product;
    }

    public static ProductEntity fromDomain(Product product) {
        ProductEntity entity = new ProductEntity();
        entity.setId(product.getId());
        entity.setName(product.getName());
        entity.setCategory(product.getCategory());
        entity.setDescription(product.getDescription());
        entity.setCreatedAt(product.getCreatedAt());
        // Note: Variants would need to be converted separately
        return entity;
    }
}