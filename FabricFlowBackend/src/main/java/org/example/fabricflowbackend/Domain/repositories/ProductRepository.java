package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.Product;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(UUID id);
    List<Product> findAll();
    List<Product> findByCategory(String category);
    void deleteById(UUID id);
    boolean existsByName(String name);
}