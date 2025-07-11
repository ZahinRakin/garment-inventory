package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.Supplier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Component
public interface SupplierRepository {
    Supplier save(Supplier supplier);
    Optional<Supplier> findById(UUID id);
    List<Supplier> findAll();
    Optional<Supplier> findByEmail(String email);
    List<Supplier> findByNameContaining(String name);
    void deleteById(UUID id);
    boolean existsById(UUID id);
}

