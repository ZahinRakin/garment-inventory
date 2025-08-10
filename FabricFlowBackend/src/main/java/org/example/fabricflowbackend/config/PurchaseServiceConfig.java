package org.example.fabricflowbackend.config;

import org.example.fabricflowbackend.Domain.repositories.PurchaseItemRepository;
import org.example.fabricflowbackend.Domain.repositories.PurchaseRepository;
import org.example.fabricflowbackend.Domain.repositories.RawMaterialRepository;
import org.example.fabricflowbackend.Domain.repositories.SupplierRepository;
import org.example.fabricflowbackend.Domain.services.PurchaseUseCase;
import org.example.fabricflowbackend.Domain.services.RawMaterialUseCase;
import org.example.fabricflowbackend.application.PurchaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PurchaseServiceConfig {
    
    @Bean
    public PurchaseUseCase purchaseUseCase(
            PurchaseRepository purchaseRepository,
            PurchaseItemRepository purchaseItemRepository,
            SupplierRepository supplierRepository,
            RawMaterialRepository rawMaterialRepository,
            RawMaterialUseCase rawMaterialService) {
        return new PurchaseService(purchaseRepository, purchaseItemRepository,
                supplierRepository, rawMaterialRepository, rawMaterialService );
    }
}
