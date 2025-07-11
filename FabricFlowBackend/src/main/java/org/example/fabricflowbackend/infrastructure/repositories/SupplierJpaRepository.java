package org.example.fabricflowbackend.infrastructure.repositories;


import org.example.fabricflowbackend.infrastructure.persistence.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SupplierJpaRepository extends JpaRepository<SupplierEntity, UUID> {
    Optional<SupplierEntity> findByEmail(String email);
    List<SupplierEntity> findByNameContaining(String name);
}