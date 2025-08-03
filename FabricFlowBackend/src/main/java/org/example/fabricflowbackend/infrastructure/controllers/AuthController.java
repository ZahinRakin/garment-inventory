package org.example.fabricflowbackend.infrastructure.controllers;

import org.example.fabricflowbackend.application.AuthService;
import org.example.fabricflowbackend.application.dto.auth.AuthResponseDto;
import org.example.fabricflowbackend.application.dto.auth.LoginRequestDto;
import org.example.fabricflowbackend.application.dto.auth.RegisterRequestDto;
import org.example.fabricflowbackend.Domain.entities.User;
import org.example.fabricflowbackend.Domain.exceptions.InvalidCredentialsException;
import org.example.fabricflowbackend.Domain.exceptions.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        try {
            String token = authService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());
            User user = authService.getCurrentUser(token);
            
            AuthResponseDto response = new AuthResponseDto();
            response.setAccessToken(token);
            
            AuthResponseDto.UserDto userDto = new AuthResponseDto.UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());
            
            response.setUser(userDto);
            
            return ResponseEntity.ok(response);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterRequestDto registerRequest) {
        logger.info("🚀 Registration Request - Starting registration for email: {}", registerRequest.getEmail());
        logger.debug("📋 Registration Request - Details: firstName={}, lastName={}, role={}", 
            registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getRole());
        
        try {
            logger.info("👤 Registration - Attempting to register user: {}", registerRequest.getEmail());
            User user = authService.registerUser(
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getRole()
            );
            logger.info("✅ Registration - User registered successfully: {} with ID: {}", user.getEmail(), user.getId());
            
            logger.info("🔑 Registration - Attempting to generate token for: {}", registerRequest.getEmail());
            String token = authService.loginUser(registerRequest.getEmail(), registerRequest.getPassword());
            logger.info("✅ Registration - Token generated successfully for: {}", registerRequest.getEmail());
            
            AuthResponseDto response = new AuthResponseDto();
            response.setAccessToken(token);
            
            AuthResponseDto.UserDto userDto = new AuthResponseDto.UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());
            
            response.setUser(userDto);
            
            logger.info("🎉 Registration - Complete success for user: {}", user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            logger.warn("⚠️ Registration - User already exists: {}", registerRequest.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            logger.error("💥 Registration - Unexpected error for user: {} - Error: {}", registerRequest.getEmail(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
} 