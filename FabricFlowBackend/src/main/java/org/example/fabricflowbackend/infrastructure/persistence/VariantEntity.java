package org.example.fabricflowbackend.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.Variant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "variants")
@Getter
@Setter
public class VariantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Column(nullable = false)
    private String size;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String fabric;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, unique = true)
    private String sku;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Variant toDomain() {
        Variant variant = new Variant(product.getId(), size, color, fabric, quantity, sku);
        variant.setId(id);
        variant.setCreatedAt(createdAt);
        return variant;
    }

    public static VariantEntity fromDomain(Variant variant) {
        VariantEntity entity = new VariantEntity();
        entity.setId(variant.getId());
        // Note: Product would need to be set separately
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(variant.getProductId());
        entity.setProduct(productEntity);
        entity.setSize(variant.getSize());
        entity.setColor(variant.getColor());
        entity.setFabric(variant.getFabric());
        entity.setQuantity(variant.getQuantity());
        entity.setSku(variant.getSku());
        entity.setCreatedAt(variant.getCreatedAt());
        return entity;
    }
}