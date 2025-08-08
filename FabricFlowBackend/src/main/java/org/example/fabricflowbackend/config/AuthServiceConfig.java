package org.example.fabricflowbackend.config;

import org.example.fabricflowbackend.Domain.repositories.UserRepository;
import org.example.fabricflowbackend.Domain.services.AuthUseCase;
import org.example.fabricflowbackend.application.AuthService;
import org.example.fabricflowbackend.infrastructure.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthServiceConfig {
    
    @Bean
    public AuthUseCase authUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        return new AuthService(userRepository, passwordEncoder, jwtUtil);
    }
}
