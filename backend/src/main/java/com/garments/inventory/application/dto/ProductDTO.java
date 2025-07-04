package com.garments.inventory.application.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProductDTO {
    private UUID id;
    private String name;
    private String category;
    private String description;
    private LocalDateTime createdAt;
    private List<VariantDTO> variants;

    // Constructors
    public ProductDTO() {}

    public ProductDTO(UUID id, String name, String category, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<VariantDTO> getVariants() { return variants; }
    public void setVariants(List<VariantDTO> variants) { this.variants = variants; }
}