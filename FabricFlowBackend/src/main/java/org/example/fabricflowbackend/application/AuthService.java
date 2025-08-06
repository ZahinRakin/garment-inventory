package org.example.fabricflowbackend.application;

import org.example.fabricflowbackend.Domain.entities.User;
import org.example.fabricflowbackend.Domain.exceptions.InvalidCredentialsException;
import org.example.fabricflowbackend.Domain.exceptions.UserAlreadyExistsException;
import org.example.fabricflowbackend.Domain.exceptions.UserNotFoundException;
import org.example.fabricflowbackend.Domain.repositories.UserRepository;
import org.example.fabricflowbackend.Domain.services.AuthUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@Service
@Transactional
public class AuthService implements AuthUseCase {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    // Remove JWT dependency completely
    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password, String role) {
        logger.info("🚀 AuthService - Starting user registration for email: {}", email);
        logger.debug("📋 AuthService - Registration details: firstName={}, lastName={}, role={}", firstName, lastName, role);
        
        // Check if user already exists
        logger.debug("🔍 AuthService - Checking if user exists with email: {}", email);
        if (userRepository.existsByEmail(email)) {
            logger.warn("⚠️ AuthService - User already exists with email: {}", email);
            throw new UserAlreadyExistsException(email);
        }
        logger.info("✅ AuthService - Email is available: {}", email);

        // Create new user
        logger.debug("🔒 AuthService - Encoding password for user: {}", email);
        String encodedPassword = passwordEncoder.encode(password);
        logger.debug("✅ AuthService - Password encoded successfully for user: {}", email);
        
        logger.info("👤 AuthService - Creating new user entity for: {}", email);
        User user = new User(firstName, lastName, email, encodedPassword, role);
        
        logger.info("💾 AuthService - Saving user to database: {}", email);
        User savedUser = userRepository.save(user);
        logger.info("✅ AuthService - User saved successfully with ID: {} for email: {}", savedUser.getId(), email);
        
        return savedUser;
    }

    // Simple login without JWT tokens
    public User simpleLogin(String email, String password) {
        logger.info("🔐 AuthService - Simple login attempt for email: {}", email);
        
        logger.debug("🔍 AuthService - Looking up user in database: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("❌ AuthService - User not found for email: {}", email);
                    return new InvalidCredentialsException();
                });
        logger.info("✅ AuthService - User found in database: {}", email);

        logger.debug("🔒 AuthService - Verifying password for user: {}", email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            logger.warn("❌ AuthService - Password mismatch for user: {}", email);
            throw new InvalidCredentialsException();
        }
        logger.info("✅ AuthService - Password verified successfully for user: {}", email);

        logger.debug("🔍 AuthService - Checking if account is enabled for user: {}", email);
        if (!user.isEnabled()) {
            logger.warn("❌ AuthService - Account is disabled for user: {}", email);
            throw new InvalidCredentialsException("Account is disabled");
        }
        logger.info("✅ AuthService - Account is enabled for user: {}", email);

        logger.info("� AuthService - Simple login successful for user: {}", email);
        return user;
    }

    @Override
    public String loginUser(String email, String password) {
        // This method still exists for compatibility but returns dummy token
        simpleLogin(email, password);
        return "no-token-needed";
    }

    @Override
    public User getCurrentUser(String token) {
        // Since we don't use tokens anymore, this method is not practical
        // But we keep it for interface compatibility
        throw new RuntimeException("Token-based authentication is disabled. Use simpleLogin instead.");
    }

    @Override
    public boolean validateToken(String token) {
        // No token validation needed
        return true;
    }
} 