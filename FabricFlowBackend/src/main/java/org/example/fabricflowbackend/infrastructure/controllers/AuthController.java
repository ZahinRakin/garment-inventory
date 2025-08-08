package org.example.fabricflowbackend.infrastructure.controllers;

import org.example.fabricflowbackend.Domain.entities.User;
import org.example.fabricflowbackend.Domain.services.AuthUseCase;
import org.example.fabricflowbackend.application.dto.auth.LoginRequest;
import org.example.fabricflowbackend.application.dto.auth.LoginResponse;
import org.example.fabricflowbackend.application.dto.auth.RegisterationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterationRequest request) {
        User user = new User(
                request.getName(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getRole()
        );

        User registeredUser = authUseCase.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authUseCase.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@RequestAttribute("username") String username) {
        return authUseCase.getAuthenticatedUser(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}