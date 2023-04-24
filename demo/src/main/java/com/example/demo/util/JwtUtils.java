package com.example.demo.util;

import com.example.demo.constant.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;

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
                .setSubject(String.valueOf(id))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
        return token;
    }

}