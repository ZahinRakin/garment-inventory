//package org.example.fabricflowbackend.config;
//
//import org.example.fabricflowbackend.Domain.repositories.ProductRepository;
//import org.example.fabricflowbackend.Domain.repositories.VariantRepository;
//import org.example.fabricflowbackend.Domain.services.ProductUseCase;
//import org.example.fabricflowbackend.application.ProductService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ProductServiceConfig {
//    @Bean
//    public ProductUseCase productUseCase(ProductRepository productRepository, VariantRepository variantRepository) {
//        return new ProductService(productRepository, variantRepository);
//    }
//}
