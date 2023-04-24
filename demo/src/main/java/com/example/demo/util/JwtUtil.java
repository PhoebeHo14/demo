package com.example.demo.util;

import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo.model.MemberAccountDto;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.crypto.MacProvider;

public class JwtUtil {
    static final long EXPIRATIONTIME = 432_000_000;     // 5天
    static final String TOKEN_PREFIX = "Bearer";        // Token前缀
    static final String HEADER_STRING = "Authorization";// 存放Token的Header Key
    static final Key key = MacProvider.generateKey();    //給定一組密鑰，用來解密以及加密使用


    // generate JWT
    public static void addAuthentication(HttpServletResponse response, MemberAccountDto memberAccountDto) {

        Date expireDate =
                //set expireTime as 30 mins
                new Date(System.currentTimeMillis() + 30 * 60 * 1000);
        String jwtToken = Jwts.builder()
                .setSubject(String.valueOf(memberAccountDto.getId()))
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        // put JWT in response
        try {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getOutputStream().println(jwtToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        // JWT authentication
        //todo jwt carry id not username
        public static Authentication getAuthentication (HttpServletRequest request){

            // get token from request header
            String token = request.getHeader(HEADER_STRING);

            if (token != null) {
                // 解析 Token
                try {
                    Claims claims = Jwts.parser()
                            // 驗證
                            .setSigningKey(key)
                            // 去掉 Bearer
                            .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                            .getBody();

                    // 拿用戶名
                    String user = claims.getSubject();

                    // 得到權限
                    List<GrantedAuthority> authorities =
                            AuthorityUtils.commaSeparatedStringToAuthorityList((String) claims.get("authorize"));
                    // 返回Token
                    return user != null ?
                            new UsernamePasswordAuthenticationToken(user, null, authorities) :
                            null;
                } catch (JwtException ex) {
                    System.out.println(ex);
                }

            }
            return null;
        }

}