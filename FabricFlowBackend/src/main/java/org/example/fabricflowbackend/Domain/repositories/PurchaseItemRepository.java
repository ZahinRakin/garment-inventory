package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.PurchaseItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public interface PurchaseItemRepository {
    PurchaseItem save(PurchaseItem purchaseItem);
    Optional<PurchaseItem> findById(UUID id);
    List<PurchaseItem> findAll();
    List<PurchaseItem> findByPurchaseId(UUID purchaseId);
    List<PurchaseItem> findByMaterialId(UUID materialId);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}
