package com.garments.inventory.domain.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Product {
    private UUID id;
    private String name;
    private String category;
    private String description;
    private LocalDateTime createdAt;
    private List<Variant> variants;

    public Product() {}

    public Product(String name, String category, String description) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.category = category;
        this.description = description;
        this.createdAt = LocalDateTime.now();
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

    public List<Variant> getVariants() { return variants; }
    public void setVariants(List<Variant> variants) { this.variants = variants; }
}