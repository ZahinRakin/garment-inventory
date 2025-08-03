package org.example.fabricflowbackend.config;

import org.example.fabricflowbackend.application.JwtService;
import org.example.fabricflowbackend.Domain.entities.User;
import org.example.fabricflowbackend.Domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestPath = request.getRequestURI();
        String method = request.getMethod();
        logger.info("🔍 JWT Filter - Processing request: {} {}", method, requestPath);
        
        // Skip JWT processing for public endpoints FIRST
        if (requestPath.equals("/api/register") || requestPath.equals("/api/login")) {
            logger.info("✅ JWT Filter - Skipping JWT processing for public endpoint: {}", requestPath);
            filterChain.doFilter(request, response);
            return;
        }
        
        final String authHeader = request.getHeader("Authorization");
        logger.debug("🔐 JWT Filter - Authorization header present: {}", authHeader != null);
        
        final String jwt;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.debug("❌ JWT Filter - No valid Authorization header found for protected endpoint: {}", requestPath);
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        logger.debug("🎫 JWT Filter - Extracted JWT token (length: {})", jwt.length());
        
        try {
            userEmail = jwtService.extractEmail(jwt);
            logger.debug("👤 JWT Filter - Extracted user email: {}", userEmail);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                logger.debug("🔍 JWT Filter - Looking up user in database for email: {}", userEmail);
                Optional<User> userOptional = userRepository.findByEmail(userEmail);
                
                if (userOptional.isPresent() && jwtService.isTokenValid(jwt, userOptional.get())) {
                    User user = userOptional.get();
                    logger.info("✅ JWT Filter - User authenticated successfully: {} (Role: {})", user.getEmail(), user.getRole());
                    
                    // Create a simple UserDetails implementation
                    UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                            .username(user.getEmail())
                            .password(user.getPassword())
                            .authorities("ROLE_" + user.getRole())
                            .build();

                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("🔐 JWT Filter - SecurityContext updated with authentication");
                } else {
                    logger.warn("❌ JWT Filter - User not found or token invalid for email: {}", userEmail);
                }
            } else {
                logger.debug("⚠️ JWT Filter - User email is null or authentication already exists");
            }
        } catch (Exception e) {
            logger.error("💥 JWT Filter - JWT validation failed: {}", e.getMessage(), e);
        }

        logger.debug("➡️ JWT Filter - Continuing filter chain for: {}", requestPath);
        filterChain.doFilter(request, response);
    }
}
