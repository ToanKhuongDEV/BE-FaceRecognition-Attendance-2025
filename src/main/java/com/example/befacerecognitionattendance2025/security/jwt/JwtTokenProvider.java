package com.example.befacerecognitionattendance2025.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import com.example.befacerecognitionattendance2025.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${jwt.access.expiration_time}")
    private long accessExpirationMinutes;

    @Value("${jwt.refresh.expiration_time}")
    private long refreshExpirationMinutes;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserPrincipal userPrincipal, boolean isRefreshToken) {
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis +
                (isRefreshToken ? refreshExpirationMinutes : accessExpirationMinutes) * 60_000;


        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .subject(userPrincipal.getId())
                .claim("username", userPrincipal.getUsername())
                .claim("scope", buildScope(userPrincipal))
                .issuedAt(new Date(nowMillis))
                .expiration(new Date(expMillis))
                .signWith(getSigningKey())
                .compact();
    }

    public Claims verifyToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.error("JWT token đã hết hạn: {}", e.getMessage());
            throw new RuntimeException("Token đã hết hạn");
        } catch (JwtException | IllegalArgumentException e) {
            log.error("JWT token không hợp lệ: {}", e.getMessage());
            throw new RuntimeException("Token không hợp lệ");
        }
    }

    public String extractUsername(String token) {
        return verifyToken(token).get("username", String.class);
    }

    public String extractUserId(String token) {
        return verifyToken(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            verifyToken(token);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = verifyToken(token);
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (JwtException e) {
            throw new RuntimeException("Token không hợp lệ");
        }
    }

    private String buildScope(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }
}