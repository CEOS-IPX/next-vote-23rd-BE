package com.ceos.voting.global.security.jwt;

import com.ceos.voting.domain.user.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String secret;
    private final Long accessTokenExpirationSeconds;

    private SecretKey secretKey;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration-seconds}") Long accessTokenExpirationSeconds
    ) {
        this.secret = secret;
        this.accessTokenExpirationSeconds = accessTokenExpirationSeconds;
    }

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenExpirationSeconds * 1000);

        return Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("name", user.getDisplayName())
                .claim("part", user.getPart().name())
                .claim("team", user.getTeam().name())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token) {
        return Long.parseLong(
                Jwts.parser().verifyWith(secretKey).build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject()
        );
    }

    public Long getAccessTokenExpirationSeconds() {
        return accessTokenExpirationSeconds;
    }
}