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

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

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
        try {
            User user = authService.registerUser(
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),
                registerRequest.getRole()
            );
            
            String token = authService.loginUser(registerRequest.getEmail(), registerRequest.getPassword());
            
            AuthResponseDto response = new AuthResponseDto();
            response.setAccessToken(token);
            
            AuthResponseDto.UserDto userDto = new AuthResponseDto.UserDto();
            userDto.setId(user.getId());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setEmail(user.getEmail());
            userDto.setRole(user.getRole());
            
            response.setUser(userDto);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
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