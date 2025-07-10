package org.example.fabricflowbackend.Domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Product {
    private UUID id;
    private String name;
    private String category;
    private String description;
    private LocalDateTime createdAt;
    private List<Variant> variants;

    public Product() {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
        this.variants = new ArrayList<>();
    }

    public Product(String name, String category, String description) {
        this();
        this.name = name;
        this.category = category;
        this.description = description;
    }