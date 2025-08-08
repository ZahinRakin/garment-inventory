package org.example.fabricflowbackend.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.Purchase;
import org.example.fabricflowbackend.Domain.entities.PurchaseItem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        // Initialize items list if null
        if (this.items == null) {
            this.items = new ArrayList<>();
        }

        // Convert all items
        List<PurchaseItem> domainItems = this.items.stream()
                .map(PurchaseItemEntity::toDomain)
                .collect(Collectors.toList());

        purchase.setItems(domainItems);

        // Recalculate total amount based on converted items
        purchase.calculateTotalAmount();
        return purchase;
    }

    public static PurchaseEntity fromDomain(Purchase purchase) {
        PurchaseEntity entity = new PurchaseEntity();
        entity.setId(purchase.getId());
        SupplierEntity supplierEntity = new SupplierEntity();
        supplierEntity.setId(purchase.getSupplierId());
        entity.setSupplier(supplierEntity);
        // Note: Supplier would need to be set separately
        entity.setOrderDate(purchase.getOrderDate());
        entity.setStatus(purchase.getStatus());
        entity.setTotalAmount(purchase.getTotalAmount());
        entity.setCreatedAt(purchase.getCreatedAt());
        
        // Convert and set purchase items
        if (purchase.getItems() != null && !purchase.getItems().isEmpty()) {
            List<PurchaseItemEntity> itemEntities = purchase.getItems().stream()
                    .map(item -> {
                        PurchaseItemEntity itemEntity = PurchaseItemEntity.fromDomain(item);
                        itemEntity.setPurchase(entity); // Set the bidirectional relationship
                        return itemEntity;
                    })
                    .collect(Collectors.toList());
            entity.setItems(itemEntities);
        } else {
            entity.setItems(new ArrayList<>());
        }
        
        return entity;
    }
}