package org.example.fabricflowbackend.infrastructure.repositories;


import org.example.fabricflowbackend.infrastructure.persistence.VariantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VariantJpaRepository extends JpaRepository<VariantEntity, UUID> {
    List<VariantEntity> findByProductId(UUID productId);
    Optional<VariantEntity> findBySku(String sku);
    List<VariantEntity> findByQuantityLessThanEqual(int threshold);
}
