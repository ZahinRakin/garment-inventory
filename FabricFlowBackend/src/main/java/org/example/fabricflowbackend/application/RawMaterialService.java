package org.example.fabricflowbackend.application;

import org.example.fabricflowbackend.Domain.entities.RawMaterial;
import org.example.fabricflowbackend.Domain.exceptions.InsufficientStockException;
import org.example.fabricflowbackend.Domain.exceptions.RawMaterialNotFoundException;
import org.example.fabricflowbackend.Domain.repositories.RawMaterialRepository;
import org.example.fabricflowbackend.Domain.services.RawMaterialUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class RawMaterialService implements RawMaterialUseCase {

    private final RawMaterialRepository rawMaterialRepository;

    @Autowired
    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @Override
    public RawMaterial createRawMaterial(RawMaterial rawMaterial) {
        if (rawMaterial.getName() == null || rawMaterial.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Raw material name cannot be null or empty");
        }

        if (rawMaterial.getUnit() == null || rawMaterial.getUnit().trim().isEmpty()) {
            throw new IllegalArgumentException("Raw material unit cannot be null or empty");
        }

        return rawMaterialRepository.save(rawMaterial);
    }

    @Override
    public RawMaterial updateRawMaterial(RawMaterial rawMaterial) {
        if (!rawMaterialRepository.existsById(rawMaterial.getId())) {
            throw new RawMaterialNotFoundException(rawMaterial.getId());
        }
        return rawMaterialRepository.save(rawMaterial);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RawMaterial> getRawMaterialById(UUID id) {
        return rawMaterialRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RawMaterial> getAllRawMaterials() {
        return rawMaterialRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RawMaterial> getRawMaterialsByCategory(String category) {
        return rawMaterialRepository.findByCategory(category);
    }

    @Override
    public void deleteRawMaterial(UUID id) {
        if (!rawMaterialRepository.existsById(id)) {
            throw new RawMaterialNotFoundException(id);
        }
        rawMaterialRepository.deleteById(id);
    }

    @Override
    public void consumeStock(UUID materialId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        RawMaterial material = rawMaterialRepository.findById(materialId)
                .orElseThrow(() -> new RawMaterialNotFoundException(materialId));

        if (material.getCurrentStock() < quantity) {
            throw new InsufficientStockException(materialId, quantity, material.getCurrentStock());
        }

        material.consumeStock(quantity);
        rawMaterialRepository.save(material);
    }

    @Override
    public void addStock(UUID materialId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        RawMaterial material = rawMaterialRepository.findById(materialId)
                .orElseThrow(() -> new RawMaterialNotFoundException(materialId));

        material.addStock(quantity);
        rawMaterialRepository.save(material);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RawMaterial> getMaterialsNeedingReorder() {
        return rawMaterialRepository.findMaterialsNeedingReorder();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RawMaterial> getLowStockMaterials(int threshold) {
        return rawMaterialRepository.findByCurrentStockLessThanEqual(threshold);
    }
}

