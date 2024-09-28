package com.ChemQuest.ChemQuest.security;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    
    private final JwtUtil jwtUtil;
    
    public JwtAuthenticationSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String username = authentication.getName();
        String token = jwtUtil.generateToken(username);
        
        // Create a cookie
        Cookie cookie = new Cookie("JWT", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); 
        cookie.setPath("/"); // The cookie will be available for the entire app
        cookie.setMaxAge(86400);
        
        response.addCookie(cookie);
        
        // Set the token as a header
        response.setHeader("Authorization", "Bearer " + token);
        
        // Redirect to dashboard
        response.sendRedirect("/student/dashboard");
    }
}