package org.example.fabricflowbackend.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.SalesItem;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "sales_items")
@Getter
@Setter
public class SalesItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_id", nullable = false)
    private SalesOrderEntity salesOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private VariantEntity variant;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public SalesItem toDomain() {
        SalesItem item = new SalesItem(salesOrder.getId(), variant.getId(), quantity, unitPrice);
        item.setId(id);
        return item;
    }

    public static SalesItemEntity fromDomain(SalesItem item) {
        SalesItemEntity entity = new SalesItemEntity();
        entity.setId(item.getId());
        // Note: SalesOrder and Variant would need to be set separately
        entity.setQuantity(item.getQuantity());
        entity.setUnitPrice(item.getUnitPrice());
        return entity;
    }
}