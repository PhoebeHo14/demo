package com.example.demo.util;

import com.example.demo.constant.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;


public class JwtUtils {

    @Value("${security.jwt.secret-key}")
    private static String secretKey;

    private static final byte[] parsedSecretKey = DatatypeConverter.parseBase64Binary(secretKey);

    private JwtUtils() {
    }

    public static String generateToken(Integer id) {

        long expiration = SecurityConstants.EXPIRATION_TIME;

        return Jwts.builder()
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim("userId", id)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    public static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(parsedSecretKey)
                .parseClaimsJws(token)
                .getBody();
    }

}