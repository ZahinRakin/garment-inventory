package org.example.fabricflowbackend.infrastructure.persistence;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.SalesOrder;
import org.example.fabricflowbackend.Domain.entities.SalesItem;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "sales_orders")
@Getter
@Setter
public class SalesOrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDate orderDate;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SalesItemEntity> items;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public SalesOrder toDomain() {
        SalesOrder order = new SalesOrder(customerName, orderDate);
        order.setId(id);
        order.setStatus(status);
        order.setTotalAmount(totalAmount);
        order.setCreatedAt(createdAt);
        
        // Initialize items list if null
        if (this.items == null) {
            this.items = new ArrayList<>();
        }

        // Convert all items
        List<SalesItem> domainItems = this.items.stream()
                .map(SalesItemEntity::toDomain)
                .collect(Collectors.toList());

        order.setItems(domainItems);

        // Recalculate total amount based on converted items
        order.calculateTotalAmount();
        return order;
    }

    public static SalesOrderEntity fromDomain(SalesOrder order) {
        SalesOrderEntity entity = new SalesOrderEntity();
        entity.setId(order.getId());
        entity.setCustomerName(order.getCustomerName());
        entity.setStatus(order.getStatus());
        entity.setOrderDate(order.getOrderDate());
        entity.setTotalAmount(order.getTotalAmount());
        entity.setCreatedAt(order.getCreatedAt());
        
        // Convert and set sales items
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            List<SalesItemEntity> itemEntities = order.getItems().stream()
                    .map(item -> {
                        SalesItemEntity itemEntity = SalesItemEntity.fromDomain(item);
                        itemEntity.setSalesOrder(entity); // Set the bidirectional relationship
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
