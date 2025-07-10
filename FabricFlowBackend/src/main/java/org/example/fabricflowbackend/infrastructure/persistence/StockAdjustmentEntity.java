package org.example.fabricflowbackend.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.StockAdjustment;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "stock_adjustments")
@Getter
@Setter
public class StockAdjustmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "item_id", nullable = false)
    private UUID itemId;

    @Column(name = "item_type", nullable = false)
    private String itemType;

    @Column(nullable = false)
    private int quantityBefore;

    @Column(nullable = false)
    private int quantityAfter;

    @Column(nullable = false)
    private int adjustmentAmount;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private String adjustedBy;

    @Column(nullable = false)
    private LocalDateTime adjustmentDate;

    public StockAdjustment toDomain() {
        StockAdjustment adjustment = new StockAdjustment(itemId, itemType, quantityBefore, quantityAfter, reason, adjustedBy);
        adjustment.setId(id);
        adjustment.setAdjustmentDate(adjustmentDate);
        return adjustment;
    }

    public static StockAdjustmentEntity fromDomain(StockAdjustment adjustment) {
        StockAdjustmentEntity entity = new StockAdjustmentEntity();
        entity.setId(adjustment.getId());
        entity.setItemId(adjustment.getItemId());
        entity.setItemType(adjustment.getItemType());
        entity.setQuantityBefore(adjustment.getQuantityBefore());
        entity.setQuantityAfter(adjustment.getQuantityAfter());
        entity.setAdjustmentAmount(adjustment.getAdjustmentAmount());
        entity.setReason(adjustment.getReason());
        entity.setAdjustedBy(adjustment.getAdjustedBy());
        entity.setAdjustmentDate(adjustment.getAdjustmentDate());
        return entity;
    }
}
