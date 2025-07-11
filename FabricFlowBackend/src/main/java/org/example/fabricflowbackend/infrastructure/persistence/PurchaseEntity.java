package org.example.fabricflowbackend.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.Purchase;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "purchases")
@Getter
@Setter
public class PurchaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private SupplierEntity supplier;

    @Column(nullable = false)
    private LocalDate orderDate;

    @Column(nullable = false)
    private String status;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItemEntity> items;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Purchase toDomain() {
        Purchase purchase = new Purchase(supplier.getId(), orderDate);
        purchase.setId(id);
        purchase.setStatus(status);
        purchase.setTotalAmount(totalAmount);
        purchase.setCreatedAt(createdAt);
        // Note: Items would need to be converted separately
        return purchase;
    }

    public static PurchaseEntity fromDomain(Purchase purchase) {
        PurchaseEntity entity = new PurchaseEntity();
        entity.setId(purchase.getId());
        // Note: Supplier would need to be set separately
        entity.setOrderDate(purchase.getOrderDate());
        entity.setStatus(purchase.getStatus());
        entity.setTotalAmount(purchase.getTotalAmount());
        entity.setCreatedAt(purchase.getCreatedAt());
        return entity;
    }
}