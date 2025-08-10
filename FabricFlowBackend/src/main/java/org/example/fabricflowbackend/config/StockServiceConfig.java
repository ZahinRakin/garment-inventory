package org.example.fabricflowbackend.config;
import org.example.fabricflowbackend.Domain.repositories.RawMaterialRepository;
import org.example.fabricflowbackend.Domain.repositories.VariantRepository;
import org.example.fabricflowbackend.Domain.services.StockUseCase;
import org.example.fabricflowbackend.application.StockService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockServiceConfig {
    
    @Bean
    public StockUseCase stockUseCase(
            VariantRepository variantRepository,
            RawMaterialRepository rawMaterialRepository) {
        return new StockService(rawMaterialRepository, variantRepository);
    }
}
