package org.example.fabricflowbackend.infrastructure.repositories;
import org.example.fabricflowbackend.infrastructure.persistence.RawMaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface RawMaterialJpaRepository extends JpaRepository<RawMaterialEntity, UUID> {
    List<RawMaterialEntity> findByCategory(String category);
    List<RawMaterialEntity> findByCurrentStockLessThanEqual(int threshold);

    @Query("SELECT r FROM RawMaterialEntity r WHERE r.currentStock <= r.reorderLevel")
    List<RawMaterialEntity> findMaterialsNeedingReorder();
}