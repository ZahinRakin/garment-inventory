package org.example.fabricflowbackend.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.RawMaterial;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "raw_materials")
@Getter
@Setter
public class RawMaterialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false)
    private int currentStock;

    @Column(nullable = false)
    private int reorderLevel;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseItemEntity> purchaseItems;

    public RawMaterial toDomain() {
        RawMaterial material = new RawMaterial(name, unit, currentStock, reorderLevel, category);
        material.setId(id);
        material.setCreatedAt(createdAt);
        return material;
    }

    public static RawMaterialEntity fromDomain(RawMaterial material) {
        RawMaterialEntity entity = new RawMaterialEntity();
        entity.setId(material.getId());
        entity.setName(material.getName());
        entity.setUnit(material.getUnit());
        entity.setCurrentStock(material.getCurrentStock());
        entity.setReorderLevel(material.getReorderLevel());
        entity.setCategory(material.getCategory());
        entity.setCreatedAt(material.getCreatedAt());
        return entity;
    }
}