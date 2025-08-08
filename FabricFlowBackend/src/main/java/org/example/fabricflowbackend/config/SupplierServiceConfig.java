package org.example.fabricflowbackend.config;

import org.example.fabricflowbackend.Domain.repositories.SupplierRepository;
import org.example.fabricflowbackend.Domain.services.SupplierUseCase;
import org.example.fabricflowbackend.application.SupplierService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupplierServiceConfig {
    
    @Bean
    public SupplierUseCase supplierUseCase(SupplierRepository supplierRepository) {
        return new SupplierService(supplierRepository);
    }
}
