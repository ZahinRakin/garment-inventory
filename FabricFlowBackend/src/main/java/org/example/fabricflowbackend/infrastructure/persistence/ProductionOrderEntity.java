package org.example.fabricflowbackend.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.ProductionOrder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "production_orders")
@Getter
@Setter
public class ProductionOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private VariantEntity variant;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String status;

    private LocalDate startDate;
    private LocalDate endDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ProductionOrder toDomain() {
        ProductionOrder order = new ProductionOrder(variant.getId(), quantity);
        order.setId(id);
        order.setStatus(status);
        order.setStartDate(startDate);
        order.setEndDate(endDate);
        order.setCreatedAt(createdAt);
        return order;
    }

    public static ProductionOrderEntity fromDomain(ProductionOrder order) {
        ProductionOrderEntity entity = new ProductionOrderEntity();
        entity.setId(order.getId());
        // Note: Variant would need to be set separately
        entity.setQuantity(order.getQuantity());
        entity.setStatus(order.getStatus());
        entity.setStartDate(order.getStartDate());
        entity.setEndDate(order.getEndDate());
        entity.setCreatedAt(order.getCreatedAt());
        return entity;
    }
}