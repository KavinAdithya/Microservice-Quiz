package com.techcrack.quizAuthService.security;

import com.techcrack.quizAuthService.service.CustomerUserDetailService;
import com.techcrack.quizAuthService.service.JwtService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SpringSecurityJwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final ApplicationContext context;

    public SpringSecurityJwtFilter(JwtService jwtService, ApplicationContext context) {
        this.jwtService = jwtService;
        this.context = context;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
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

        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null)
            return;

        UserDetails userDetails = context.getBean(CustomerUserDetailService.class)
                .loadUserByUsername(username);

        if (!jwtService.validateToken(token))
            return;

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
