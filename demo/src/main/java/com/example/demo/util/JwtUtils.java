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

//    private static final byte[] secretKey = DatatypeConverter.parseBase64Binary(secret);

    private JwtUtils() {
    }

    public static String generateToken(Integer id, String secret) {

        long expiration = SecurityConstants.EXPIRATION_TIME;

        return Jwts.builder()
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .signWith(SignatureAlgorithm.HS256, DatatypeConverter.parseBase64Binary(secret))
                .claim("userId", id)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    public static Claims getTokenBody(String token, String secret) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(token)
                .getBody();
    }

}