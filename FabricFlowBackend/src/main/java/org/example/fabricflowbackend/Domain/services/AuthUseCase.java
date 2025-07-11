package org.example.fabricflowbackend.Domain.services;

import org.example.fabricflowbackend.Domain.entities.User;

public interface AuthUseCase {
    User registerUser(String firstName, String lastName, String email, String password, String role);
    String loginUser(String email, String password);
    User getCurrentUser(String token);
    boolean validateToken(String token);
} 