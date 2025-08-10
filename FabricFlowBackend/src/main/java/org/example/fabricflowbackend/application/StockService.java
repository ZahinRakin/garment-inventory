package org.example.fabricflowbackend.application;

import org.example.fabricflowbackend.Domain.entities.RawMaterial;
import org.example.fabricflowbackend.Domain.entities.Variant;
import org.example.fabricflowbackend.Domain.entities.StockAdjustment;
import org.example.fabricflowbackend.Domain.exceptions.InsufficientStockException;
import org.example.fabricflowbackend.Domain.exceptions.RawMaterialNotFoundException;
import org.example.fabricflowbackend.Domain.exceptions.VariantNotFoundException;
import org.example.fabricflowbackend.Domain.repositories.RawMaterialRepository;
import org.example.fabricflowbackend.Domain.repositories.VariantRepository;
import org.example.fabricflowbackend.Domain.repositories.StockAdjustmentRepository;
import org.example.fabricflowbackend.Domain.services.StockUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StockService implements StockUseCase {

    private final RawMaterialRepository rawMaterialRepository;
    private final VariantRepository variantRepository;
    public StockService(RawMaterialRepository rawMaterialRepository,
                        VariantRepository variantRepository,
                        StockAdjustmentRepository stockAdjustmentRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
        this.variantRepository = variantRepository;
        this.stockAdjustmentRepository = stockAdjustmentRepository;
    }

    @Override
    public void adjustRawMaterialStock(UUID materialId, int quantity, String reason) {
        RawMaterial material = rawMaterialRepository.findById(materialId)
                .orElseThrow(() -> new RawMaterialNotFoundException(materialId));

        if (quantity > 0) {
            material.addStock(quantity);
        } else if (quantity < 0) {
            int absoluteQuantity = Math.abs(quantity);
            if (material.getCurrentStock() < absoluteQuantity) {
                throw new InsufficientStockException(materialId, absoluteQuantity, material.getCurrentStock());
            }
            material.consumeStock(absoluteQuantity);
        }
        rawMaterialRepository.save(material);
    }

    @Override
    public void transferRawMaterialStock(UUID fromMaterialId, UUID toMaterialId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Transfer quantity must be positive");
        }

        RawMaterial fromMaterial = rawMaterialRepository.findById(fromMaterialId)
                .orElseThrow(() -> new RawMaterialNotFoundException(fromMaterialId));

        RawMaterial toMaterial = rawMaterialRepository.findById(toMaterialId)
                .orElseThrow(() -> new RawMaterialNotFoundException(toMaterialId));

        if (fromMaterial.getCurrentStock() < quantity) {
            throw new InsufficientStockException(fromMaterialId, quantity, fromMaterial.getCurrentStock());
        }

        fromMaterial.consumeStock(quantity);
        toMaterial.addStock(quantity);

        rawMaterialRepository.save(fromMaterial);
        rawMaterialRepository.save(toMaterial);
    }

    @Override
    public List<RawMaterial> getRawMaterialsWithLowStock() {
        return rawMaterialRepository.findMaterialsNeedingReorder();
    }

    @Override
    public void adjustVariantStock(UUID variantId, int quantity, String reason) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new VariantNotFoundException(variantId));

        if (quantity > 0) {
            variant.increaseStock(quantity);
        } else if (quantity < 0) {
            int absoluteQuantity = Math.abs(quantity);
            if (variant.getQuantity() < absoluteQuantity) {
                throw new InsufficientStockException(variantId, absoluteQuantity, variant.getQuantity());
            }
            variant.reduceStock(absoluteQuantity);
        }
        variantRepository.save(variant);
    }

    @Override
    public void transferVariantStock(UUID fromVariantId, UUID toVariantId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Transfer quantity must be positive");
        }

        Variant fromVariant = variantRepository.findById(fromVariantId)
                .orElseThrow(() -> new VariantNotFoundException(fromVariantId));

        Variant toVariant = variantRepository.findById(toVariantId)
                .orElseThrow(() -> new VariantNotFoundException(toVariantId));

        if (fromVariant.getQuantity() < quantity) {
            throw new InsufficientStockException(fromVariantId, quantity, fromVariant.getQuantity());
        }

        fromVariant.reduceStock(quantity);
        toVariant.increaseStock(quantity);

        variantRepository.save(fromVariant);
        variantRepository.save(toVariant);
    }

    @Override
    public List<Variant> getVariantsWithLowStock() {
        return variantRepository.findByQuantityLessThanEqual(10); // Default threshold of 10
    }

    @Override
    public boolean validateRawMaterialStock(UUID materialId, int requiredQuantity) {
        RawMaterial material = rawMaterialRepository.findById(materialId)
                .orElseThrow(() -> new RawMaterialNotFoundException(materialId));
        return material.getCurrentStock() >= requiredQuantity;
    }

    @Override
    public boolean validateVariantStock(UUID variantId, int requiredQuantity) {
        Variant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new VariantNotFoundException(variantId));
        return variant.getQuantity() >= requiredQuantity;
    }

    @Override
    public List<String> getStockAlerts() {
        List<String> alerts = new ArrayList<>();

        // Raw material alerts
        rawMaterialRepository.findMaterialsNeedingReorder().forEach(material -> {
            alerts.add(String.format("Low stock alert: Raw Material %s (Current: %d, Reorder Level: %d)",
                    material.getName(), material.getCurrentStock(), material.getReorderLevel()));
        });

        // Variant alerts
        variantRepository.findByQuantityLessThanEqual(5).forEach(variant -> { // Threshold of 5
            alerts.add(String.format("Low stock alert: Variant %s (Current: %d)",
                    variant.getSku(), variant.getQuantity()));
        });

        return alerts;
    }

    @Override
    public void generateStockReport() {
        // In a real implementation, this would generate a detailed report
        // For now, we'll just log the current stock status
        System.out.println("=== Stock Report ===");
        System.out.println("Raw Materials:");
        rawMaterialRepository.findAll().forEach(material -> {
            System.out.printf("- %s: %d %s (Reorder at %d)%n",
                    material.getName(), material.getCurrentStock(),
                    material.getUnit(), material.getReorderLevel());
        });

        System.out.println("Finished Goods:");
        variantRepository.findAll().forEach(variant -> {
            System.out.printf("- %s: %d in stock%n", variant.getSku(), variant.getQuantity());
        });
    }

    @Override
    public List<StockAdjustment> getAllStockAdjustments() {
        return stockAdjustmentRepository.findAll();
    }
}