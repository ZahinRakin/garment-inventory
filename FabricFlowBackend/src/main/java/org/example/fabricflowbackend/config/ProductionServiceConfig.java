package org.example.fabricflowbackend.config;

import org.example.fabricflowbackend.Domain.repositories.ProductionOrderRepository;
import org.example.fabricflowbackend.Domain.repositories.ProductRepository;
import org.example.fabricflowbackend.Domain.repositories.RawMaterialRepository;
import org.example.fabricflowbackend.Domain.repositories.VariantRepository;
import org.example.fabricflowbackend.Domain.services.ProductionUseCase;
import org.example.fabricflowbackend.application.ProductionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductionServiceConfig {
    
    @Bean
    public ProductionUseCase productionUseCase(
            ProductionOrderRepository productionOrderRepository,
            VariantRepository variantRepository) {
        return new ProductionService(productionOrderRepository,variantRepository);
    }
}
