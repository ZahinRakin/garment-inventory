package com.garments.inventory.domain.repositories;

import com.garments.inventory.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findByCategory(String category);
    // You can add custom query methods here if needed
}