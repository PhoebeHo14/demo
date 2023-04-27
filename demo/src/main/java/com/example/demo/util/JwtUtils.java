package com.example.demo.util;

import com.example.demo.constant.SecurityConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static final byte[] secretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);


    // generate JWT
    public static String generateToken(Integer id) {
        byte[] jwtSecretKey = DatatypeConverter.parseBase64Binary(SecurityConstants.JWT_SECRET_KEY);

        long expiration = SecurityConstants.EXPIRATION_TIME;

        String token = Jwts.builder()
                .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
                .signWith(SignatureAlgorithm.HS256, Keys.hmacShaKeyFor(jwtSecretKey))
                .claim("id", id)
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
        return token;
    }

    public static boolean validateToken(String token) {
        try {
            getTokenBody(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
        }
        return false;
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

}