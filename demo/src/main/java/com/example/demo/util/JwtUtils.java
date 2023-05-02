package com.example.demo.util;

import com.example.demo.constant.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Component
public class JwtUtils {

    private static final byte[] secretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);

    private JwtUtils() {
    }

    public static String generateToken(Integer id) {
        byte[] jwtSecretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);

        long expiration = SecurityConstants.EXPIRATION_TIME;

        return Jwts.builder()
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey), SignatureAlgorithm.HS256)
                .claim("userId", id)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    public static Claims getTokenBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}