package org.example.fabricflowbackend.infrastructure.persistence;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.fabricflowbackend.Domain.entities.Supplier;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseEntity> purchases;

    public Supplier toDomain() {
        Supplier supplier = new Supplier(name, email, phone, address);
        supplier.setId(id);
        supplier.setCreatedAt(createdAt);
        return supplier;
    }

    public static SupplierEntity fromDomain(Supplier supplier) {
        SupplierEntity entity = new SupplierEntity();
        entity.setId(supplier.getId());
        entity.setName(supplier.getName());
        entity.setEmail(supplier.getEmail());
        entity.setPhone(supplier.getPhone());
        entity.setAddress(supplier.getAddress());
        entity.setCreatedAt(supplier.getCreatedAt());
        return entity;
    }
}
