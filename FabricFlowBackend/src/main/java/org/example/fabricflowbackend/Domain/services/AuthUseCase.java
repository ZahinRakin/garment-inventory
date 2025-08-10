package org.example.fabricflowbackend.Domain.services;

import org.example.fabricflowbackend.Domain.entities.User;
import java.util.Optional;

public interface AuthUseCase {
    User register(User user);
    String login(String username, String password);
    Optional<User> getAuthenticatedUser(String username);
}