import java.math.BigDecimal;
import java.util.UUID;

public class SalesItem {
    private UUID id;
    private UUID salesOrderId;
    private UUID variantId;
    private int quantity;
    private BigDecimal unitPrice;

    public SalesItem() {}

    public SalesItem(UUID salesOrderId, UUID variantId, int quantity, BigDecimal unitPrice) {
        this.id = UUID.randomUUID();
        this.salesOrderId = salesOrderId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getSalesOrderId() { return salesOrderId; }
    public void setSalesOrderId(UUID salesOrderId) { this.salesOrderId = salesOrderId; }

    public UUID getVariantId() { return variantId; }
    public void setVariantId(UUID variantId) { this.variantId = variantId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}