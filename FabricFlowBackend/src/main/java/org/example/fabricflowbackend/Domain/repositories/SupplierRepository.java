package org.example.fabricflowbackend.Domain.repositories;

import org.example.fabricflowbackend.Domain.entities.Supplier;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository {
    Supplier save(Supplier supplier);
    Optional<Supplier> findById(UUID id);
    List<Supplier> findAll();
    Optional<Supplier> findByName(String name);
    Optional<Supplier> findByEmail(String email);
    void deleteById(UUID id);
    boolean existsByEmail(String email);
}