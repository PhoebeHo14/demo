package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;


public class JwtUtil {

    private JwtUtil() {
    }

    public static String generateToken(Integer id, String secret) {

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .signWith(SignatureAlgorithm.HS256, DatatypeConverter.parseBase64Binary(secret))
                .claim("userId", id)
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 2L * 1000))  //2hrs
                .compact();
    }

    public static Claims getTokenBody(String token, String secret) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(token)
                .getBody();
    }

}