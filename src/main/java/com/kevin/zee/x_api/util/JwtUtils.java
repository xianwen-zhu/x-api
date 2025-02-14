package com.kevin.zee.x_api.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    private final String SECRET_KEY = "ibbOs2zqf6/HL5P+SoqBuGFp1USJtt1o6UW0lN8i1WI="; // **32 字节密钥**
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // 生成 JWT 令牌
    public String generateToken(String username) {
        // **1 天有效期（单位：毫秒）**
        long EXPIRATION_TIME = 86400000;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 生成刷新令牌
    public String generateRefreshToken(String username) {
        // **30 天有效期**
        long REFRESH_EXPIRATION_TIME = 2592000000L;
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 解析 JWT
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}