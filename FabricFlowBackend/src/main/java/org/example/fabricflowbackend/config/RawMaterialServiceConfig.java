package org.example.fabricflowbackend.config;

import org.example.fabricflowbackend.Domain.repositories.RawMaterialRepository;
import org.example.fabricflowbackend.Domain.services.RawMaterialUseCase;
import org.example.fabricflowbackend.application.RawMaterialService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RawMaterialServiceConfig {
    
    @Bean
    public RawMaterialUseCase rawMaterialUseCase(RawMaterialRepository rawMaterialRepository) {
        return new RawMaterialService(rawMaterialRepository);
    }
}
