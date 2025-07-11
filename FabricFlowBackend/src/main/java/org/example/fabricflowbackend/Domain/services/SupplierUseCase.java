package org.example.fabricflowbackend.Domain.services;

import org.example.fabricflowbackend.Domain.entities.Supplier;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SupplierUseCase {
    Supplier createSupplier(Supplier supplier);
    Supplier updateSupplier(Supplier supplier);
    Optional<Supplier> getSupplierById(UUID id);
    List<Supplier> getAllSuppliers();
    List<Supplier> findSuppliersByName(String name);
    void deleteSupplier(UUID id);
    boolean isEmailUnique(String email);
}