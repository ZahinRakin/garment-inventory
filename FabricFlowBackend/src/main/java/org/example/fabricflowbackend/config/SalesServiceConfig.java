package org.example.fabricflowbackend.config;

import org.example.fabricflowbackend.Domain.repositories.SalesItemRepository;
import org.example.fabricflowbackend.Domain.repositories.SalesOrderRepository;
import org.example.fabricflowbackend.Domain.repositories.VariantRepository;
import org.example.fabricflowbackend.Domain.services.SalesUseCase;
import org.example.fabricflowbackend.application.SalesService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SalesServiceConfig {
    
    @Bean
    public SalesUseCase salesUseCase(
            SalesOrderRepository salesOrderRepository,
            SalesItemRepository salesItemRepository,
            VariantRepository variantRepository) {
        return new SalesService(salesOrderRepository, salesItemRepository, variantRepository);
    }
}
