package com.garments.inventory.application.dto;

public class AuthResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String username;
    private String role;

    public AuthResponseDTO() {}
    public AuthResponseDTO(String accessToken, String refreshToken, String username, String role) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.role = role;
    }
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}

