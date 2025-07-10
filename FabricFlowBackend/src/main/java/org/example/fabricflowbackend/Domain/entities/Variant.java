import java.time.LocalDateTime;
import java.util.UUID;

public class Variant {
    private UUID id;
    private UUID productId;
    private String size;
    private String color;
    private String fabric;
    private Integer quantity;
    private String sku;
    private LocalDateTime createdAt;

    public Variant() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.quantity = 0;
    }

    public Variant(UUID productId, String size, String color, String fabric, String sku) {
        this();
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.fabric = fabric;
        this.sku = sku;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getFabric() { return fabric; }
    public void setFabric(String fabric) { this.fabric = fabric; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public void updateStock(int quantity) {
        this.quantity += quantity;
    }

    public boolean canFulfillOrder(int requestedQuantity) {
        return this.quantity >= requestedQuantity;
    }
}