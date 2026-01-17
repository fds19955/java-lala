package org.example.articlesystem.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret:your-very-long-secret-key-at-least-32-characters-long}")
    private String secretKey;

    @Value("${jwt.expiration:86400000}") // 默认24小时
    private long expiration;

    // 生成安全的签名密钥
    private SecretKey getSigningKey() {
        // 确保密钥长度足够，避免 WeakKeyException
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 生成JWT令牌（增强版，可存储角色等信息）
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username) // 设置主题（通常是用户名）
                .claim("role", role) // 添加自定义声明，如用户角色
                .issuedAt(new Date()) // 设置签发时间
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 设置过期时间
                .signWith(getSigningKey(), Jwts.SIG.HS256) // 使用新版本API签名
                .compact();
    }

    // 从令牌中提取用户名（主题）
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 从令牌中提取角色（自定义声明）
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // 验证令牌有效性
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey()) // 新版本API，使用 verifyWith
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // 日志记录异常 e，便于调试
            return false;
        }
    }

    // 通用声明提取方法
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 解析令牌中的所有声明
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // 新版本API
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}