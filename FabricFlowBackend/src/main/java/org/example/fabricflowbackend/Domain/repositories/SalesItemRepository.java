package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.SalesItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public interface SalesItemRepository {
    SalesItem save(SalesItem salesItem);
    Optional<SalesItem> findById(UUID id);
    List<SalesItem> findAll();
    List<SalesItem> findBySalesOrderId(UUID salesOrderId);
    List<SalesItem> findByVariantId(UUID variantId);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}
