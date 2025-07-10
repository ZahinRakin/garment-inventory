ackage org.example.fabricflowbackend.Domain.repositories;

import com.garments.inventory.domain.entities.SalesItem;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SalesItemRepository {
    SalesItem save(SalesItem salesItem);
    Optional<SalesItem> findById(UUID id);
    List<SalesItem> findAll();
    List<SalesItem> findBySalesOrderId(UUID salesOrderId);
    List<SalesItem> findByVariantId(UUID variantId);
    void deleteById(UUID id);
    void deleteBySalesOrderId(UUID salesOrderId);
}