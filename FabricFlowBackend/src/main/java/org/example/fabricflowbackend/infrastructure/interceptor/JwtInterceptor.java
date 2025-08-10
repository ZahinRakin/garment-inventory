package org.example.fabricflowbackend.infrastructure.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.fabricflowbackend.infrastructure.annotation.RoleAllowed;
import org.example.fabricflowbackend.infrastructure.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        // Skip if not a handler method (e.g., for resource requests)
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // Check for @RoleAllowed annotation
        RoleAllowed roleAllowed = handlerMethod.getMethodAnnotation(RoleAllowed.class);

        // If endpoint doesn't require any role, skip JWT check
        if (roleAllowed == null) {
            return true;
        }

        // Verify JWT token
        String authHeader = req.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Missing or invalid token");
            return false;
        }

        try {
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.validateToken(token);

            // Set user attributes
            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            req.setAttribute("username", username);
            req.setAttribute("role", role);

            // Check role authorization
            List<String> allowedRoles = Arrays.asList(roleAllowed.value());
            if (!allowedRoles.contains(role)) {
                res.setStatus(HttpServletResponse.SC_FORBIDDEN);
                res.getWriter().write("Insufficient privileges");
                return false;
            }

            return true;

        } catch (Exception e) {
            res.setStatus(HttpServletResponse.SC_FORBIDDEN);
            res.getWriter().write("Token invalid or expired");
            return false;
        }
    }
}