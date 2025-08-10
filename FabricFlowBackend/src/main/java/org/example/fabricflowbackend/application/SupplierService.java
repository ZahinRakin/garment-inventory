package org.example.fabricflowbackend.application;

import org.example.fabricflowbackend.Domain.entities.Supplier;
import org.example.fabricflowbackend.Domain.exceptions.SupplierNotFoundException;
import org.example.fabricflowbackend.Domain.repositories.SupplierRepository;
import org.example.fabricflowbackend.Domain.services.SupplierUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SupplierService implements SupplierUseCase {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Supplier createSupplier(Supplier supplier) {
        if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Supplier name cannot be null or empty");
        }

        if (supplier.getEmail() != null && !isEmailUnique(supplier.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier updateSupplier(Supplier supplier) {
        if (!supplierRepository.existsById(supplier.getId())) {
            throw new SupplierNotFoundException(supplier.getId());
        }

        // Check email uniqueness if changed
        Optional<Supplier> existingSupplier = supplierRepository.findById(supplier.getId());
        if (existingSupplier.isPresent() &&
                !existingSupplier.get().getEmail().equals(supplier.getEmail()) &&
                !isEmailUnique(supplier.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        return supplierRepository.save(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Supplier> getSupplierById(UUID id) {
        return supplierRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> findSuppliersByName(String name) {
        return supplierRepository.findByNameContaining(name);
    }

    @Override
    public void deleteSupplier(UUID id) {
        if (!supplierRepository.existsById(id)) {
            throw new SupplierNotFoundException(id);
        }
        supplierRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isEmailUnique(String email) {
        return supplierRepository.findByEmail(email).isEmpty();
    }
}
