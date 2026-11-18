package com.techcrack.quizApiGateway.security;


import com.techcrack.quizApiGateway.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;

import java.io.IOException;
import java.util.List;

@Component
public class SpringSecurityJwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;


    public SpringSecurityJwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        authenticateCurrentUser(request, response);
        filterChain.doFilter(request, response);
    }

    private void authenticateCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer"))
            return;

        String token = authHeader.substring(7);
        String username = null;
        try {
            username = jwtService.extractUsername(token);

            if (username == null || SecurityContextHolder.getContext().getAuthentication() != null)
                return;

            if (!jwtService.validateToken(token))
                return;

        } catch (JwtException | IllegalArgumentException ex) {
            return;
        }


        List<SimpleGrantedAuthority> roles = jwtService.extractRoles(token);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                roles
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
