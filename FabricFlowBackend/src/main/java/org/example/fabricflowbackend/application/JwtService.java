package org.example.fabricflowbackend.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.fabricflowbackend.Domain.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    @Value("${jwt.secret:defaultSecretKeyForDevelopmentOnly}")
    private String secret;

    @Value("${jwt.expiration:86400000}")
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User user) {
        logger.info("🎫 JwtService - Starting token generation for user: {}", user.getEmail());
        logger.debug("📋 JwtService - User details: ID={}, Role={}", user.getId(), user.getRole());
        
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());
        claims.put("userId", user.getId().toString());
        logger.debug("✅ JwtService - Claims prepared: role={}, userId={}", user.getRole(), user.getId());
        
        String token = createToken(claims, user.getEmail());
        logger.info("✅ JwtService - Token generated successfully for user: {} (length: {})", user.getEmail(), token.length());
        
        return token;
    }

    private String createToken(Map<String, Object> claims, String subject) {
        logger.debug("🔨 JwtService - Creating token with subject: {}", subject);
        logger.debug("📋 JwtService - Token claims: {}", claims);
        logger.debug("⏰ JwtService - Token expiration time: {} ms", expiration);
        
        try {
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
            
            logger.debug("✅ JwtService - Token created successfully");
            return token;
        } catch (Exception e) {
            logger.error("💥 JwtService - Error creating token: {}", e.getMessage(), e);
            throw e;
        }
    }

   

    public Boolean isTokenValid(String token, User user) {
        logger.debug("🔍 JwtService - Checking token validity for user: {}", user.getEmail());
        try {
            final String email = extractEmail(token);
            logger.debug("📧 JwtService - Extracted email from token: {}", email);
            
            boolean emailMatch = email.equals(user.getEmail());
            boolean notExpired = !isTokenExpired(token);
            
            logger.debug("📧 JwtService - Email match: {}, Token not expired: {}", emailMatch, notExpired);
            
            boolean isValid = emailMatch && notExpired;
            logger.debug("✅ JwtService - Token validity result for {}: {}", user.getEmail(), isValid);
            
            return isValid;
        } catch (Exception e) {
            logger.error("❌ JwtService - Error checking token validity for {}: {}", user.getEmail(), e.getMessage());
            return false;
        }
    }

 public Boolean validateToken(String token) {
        logger.debug("🔍 JwtService - Validating token (length: {})", token.length());
        try {
            boolean isValid = !isTokenExpired(token);
            logger.debug("✅ JwtService - Token validation result: {}", isValid);
            return isValid;
        } catch (Exception e) {
            logger.error("❌ JwtService - Token validation failed: {}", e.getMessage());
            return false;
        }
    }



} 