package com.example.demo.filter;

import com.example.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    String secret;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            try {
                String token = authHeader.replace("Bearer ", "");
                Claims claim = JwtUtil.getTokenBody(token, secret);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(claim.get("userId").toString(), null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException |
                     IllegalArgumentException e) {
                throw new AuthenticationServiceException("invalid authentication");
            }
        }
        chain.doFilter(request, response);
    }

}