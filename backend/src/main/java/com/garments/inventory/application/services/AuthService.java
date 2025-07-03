package com.garments.inventory.application.services;

import com.garments.inventory.application.dto.AuthRequestDTO;
import com.garments.inventory.application.dto.AuthResponseDTO;
import com.garments.inventory.application.dto.UserRegistrationDTO;
import com.garments.inventory.domain.entities.User;
import com.garments.inventory.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Map<String, String> refreshTokenStore = new ConcurrentHashMap<>();

    public AuthResponseDTO register(UserRegistrationDTO registrationDTO) {
        if (userRepository.findByUsername(registrationDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        String hashedPassword = passwordEncoder.encode(registrationDTO.getPassword());
        User user = new User(registrationDTO.getUsername(), hashedPassword, registrationDTO.getEmail(), registrationDTO.getRole());
        userRepository.save(user);
        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        refreshTokenStore.put(user.getUsername(), refreshToken);
        return new AuthResponseDTO(accessToken, refreshToken, user.getUsername(), user.getRole());
    }

    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        Optional<User> userOpt = userRepository.findByUsername(authRequest.getUsername());
        if (userOpt.isEmpty() || !passwordEncoder.matches(authRequest.getPassword(), userOpt.get().getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        User user = userOpt.get();
        String accessToken = jwtUtil.generateToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        refreshTokenStore.put(user.getUsername(), refreshToken);
        return new AuthResponseDTO(accessToken, refreshToken, user.getUsername(), user.getRole());
    }

    public AuthResponseDTO refreshToken(String refreshToken) {
        String username = jwtUtil.getUsernameFromToken(refreshToken.replace("Bearer ", ""));
        if (!refreshToken.equals(refreshTokenStore.get(username)) || !jwtUtil.validateRefreshToken(refreshToken.replace("Bearer ", ""))) {
            throw new RuntimeException("Invalid refresh token");
        }
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) throw new RuntimeException("User not found");
        User user = userOpt.get();
        String newAccessToken = jwtUtil.generateToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);
        refreshTokenStore.put(username, newRefreshToken);
        return new AuthResponseDTO(newAccessToken, newRefreshToken, user.getUsername(), user.getRole());
    }

    public void logout(String token) {
        String username = jwtUtil.getUsernameFromToken(token.replace("Bearer ", ""));
        refreshTokenStore.remove(username);
    }
}
