package org.example.fabricflowbackend.application.dto.auth;
public class LoginResponse {
    private String token;

    // Default constructor
    public LoginResponse() {
    }

    // Parameterized constructor
    public LoginResponse(String token) {
        this.token = token;
    }

    // Getter and setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
