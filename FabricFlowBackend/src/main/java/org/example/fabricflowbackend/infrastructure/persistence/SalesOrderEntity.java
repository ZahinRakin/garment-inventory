package org.example.fabricflowbackend.infrastructure.persistence;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.SalesOrder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        // Note: Items would need to be converted separately
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
        return entity;
    }
}
