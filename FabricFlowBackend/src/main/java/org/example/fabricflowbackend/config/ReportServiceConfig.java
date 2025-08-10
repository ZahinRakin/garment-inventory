package org.example.fabricflowbackend.config;

import org.example.fabricflowbackend.Domain.repositories.*;
import org.example.fabricflowbackend.Domain.services.ReportUseCase;
import org.example.fabricflowbackend.application.ReportService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReportServiceConfig {
    
    @Bean
    public ReportUseCase reportUseCase(
            RawMaterialRepository rawMaterialRepository,
            VariantRepository variantRepository,
            PurchaseRepository purchaseRepository,
            SalesOrderRepository salesOrderRepository,
            ProductionOrderRepository productionOrderRepository,
            AlertRepository alertRepository, SalesItemRepository salesItemRepository) {
        return new ReportService(
                rawMaterialRepository,variantRepository,
                purchaseRepository,salesOrderRepository,
                productionOrderRepository,alertRepository,
                salesItemRepository
        );
    }
}
