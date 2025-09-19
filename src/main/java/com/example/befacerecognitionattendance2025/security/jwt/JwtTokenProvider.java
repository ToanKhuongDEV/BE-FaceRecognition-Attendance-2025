package com.example.befacerecognitionattendance2025.security.jwt;

import io.jsonwebtoken.Claims;
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
                .claims()
                .add("sub", userPrincipal.getId())
                .add("username", userPrincipal.getUsername())
                .add("scope", buildScope(userPrincipal))
                .and()
                .issuedAt(new Date(nowMillis))
                .expiration(new Date(expMillis))
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();

    }

    public Claims verifyToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException e) {
            throw new RuntimeException("Token không hợp lệ hoặc hết hạn");
        }
    }

    private String buildScope(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
    }

}
