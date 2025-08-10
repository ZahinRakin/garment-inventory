package org.example.fabricflowbackend.infrastructure.persistence;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.PurchaseItem;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "purchase_items")
@Getter
@Setter
public class PurchaseItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private PurchaseEntity purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", nullable = false)
    private RawMaterialEntity material;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;

    public PurchaseItem toDomain() {
        PurchaseItem item = new PurchaseItem(purchase.getId(), material.getId(), quantity, unitPrice);
        item.setId(id);
        return item;
    }

    public static PurchaseItemEntity fromDomain(PurchaseItem item) {
        PurchaseItemEntity entity = new PurchaseItemEntity();
        entity.setId(item.getId());
        // Note: Purchase and Material would need to be set separately
        PurchaseEntity purchaseEntity = new PurchaseEntity();
        purchaseEntity.setId(item.getPurchaseId());
        entity.setPurchase(purchaseEntity);
        RawMaterialEntity materialEntity = new RawMaterialEntity();
        materialEntity.setId(item.getMaterialId());
        entity.setMaterial(materialEntity);
        entity.setQuantity(item.getQuantity());
        entity.setUnitPrice(item.getUnitPrice());
        return entity;
    }
}
