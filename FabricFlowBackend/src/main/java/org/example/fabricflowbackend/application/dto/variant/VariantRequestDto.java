package org.example.fabricflowbackend.application.dto.variant;



import java.util.UUID;

public class VariantRequestDto {
    private UUID productId;
    private String size;
    private String color;
    private String fabric;
    private int quantity;
    private String sku;

    // Getters and Setters
    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }
    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public String getFabric() { return fabric; }
    public void setFabric(String fabric) { this.fabric = fabric; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }
}