package com.example.demo.filter;

import com.example.demo.constant.SecurityConstants;
import com.example.demo.util.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {

        String token = this.getTokenFromHttpRequest(request);

        if (JwtUtils.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_OK);
//            filterChain.doFilter(request, response);
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String getTokenFromHttpRequest(HttpServletRequest request) {
        String authorization = request.getHeader(SecurityConstants.TOKEN_HEADER);
        if (authorization == null || !authorization.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return null;
        }
        return authorization.replace(SecurityConstants.TOKEN_PREFIX, "");
    }

}
