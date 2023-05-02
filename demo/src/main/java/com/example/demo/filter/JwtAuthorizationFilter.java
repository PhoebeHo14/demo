package com.example.demo.filter;

import com.example.demo.util.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);
        if (authHeader != null) {
            try {
                String token = authHeader.replace("Bearer ", "");
                Claims claim = jwtUtils.getTokenBody(token);
                System.out.println("pass");
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                     IllegalArgumentException e) {
                System.out.println(e);
                throw new AuthenticationServiceException("invalid authentication");
            }
        }
        chain.doFilter(request, response);
    }
}