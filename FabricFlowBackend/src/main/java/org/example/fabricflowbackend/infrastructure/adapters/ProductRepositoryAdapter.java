package org.example.fabricflowbackend.infrastructure.adapters;



import org.example.fabricflowbackend.Domain.entities.Product;
import org.example.fabricflowbackend.Domain.repositories.ProductRepository;
import org.example.fabricflowbackend.infrastructure.persistence.ProductEntity;
import org.example.fabricflowbackend.infrastructure.repositories.ProductJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryAdapter implements ProductRepository {
    private final ProductJpaRepository jpaRepository;

    public ProductRepositoryAdapter(ProductJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Product save(Product product) {
        System.out.println("Saving product " + product.getName());
        ProductEntity entity = ProductEntity.fromDomain(product);
        System.out.println("Saving product " + product.getName() + " with id " + entity.getId());
        entity = jpaRepository.save(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return jpaRepository.findById(id).map(ProductEntity::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findByCategory(String category) {
        return jpaRepository.findByCategory(category).stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findByName(String name) {
        return jpaRepository.findByName(name).map(ProductEntity::toDomain);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return jpaRepository.existsById(id);
    }
}