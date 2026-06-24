package com.ceos.voting.global.security.jwt;

import com.ceos.voting.domain.user.domain.User;
import com.ceos.voting.global.exception.BusinessException;
import com.ceos.voting.global.exception.ErrorCode;
import com.ceos.voting.global.security.dto.AccessTokenInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {
    private final SecretKey key;
    private final Long accessTokenExpirationSeconds;
    private final long refreshTokenExpirationSeconds;
    private final JwtParser jwtParser;

    private static final String ROLE_KEY = "role";
    private static final String TOKEN_TYPE_KEY = "type";
    private static final String ACCESS_TOKEN_TYPE = "ACCESS";


    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.access-token-expiration-seconds}") Long accessTokenExpirationSeconds,
            @Value("${jwt.refresh-token-validity-seconds}") Long refreshTokenExpirationSeconds
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpirationSeconds = accessTokenExpirationSeconds;
        this.refreshTokenExpirationSeconds = refreshTokenExpirationSeconds;

        this.jwtParser = Jwts.parser()
                .verifyWith(this.key)
                .build();
    }

    public String createAccessToken(Long userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenExpirationSeconds * 1000);

        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim(TOKEN_TYPE_KEY, ACCESS_TOKEN_TYPE)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    public String createRefreshToken() {
        return UUID.randomUUID().toString();
    }

    public Claims parseClaims(String token) {
        try {
            return jwtParser.parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
            throw new BusinessException(ErrorCode.EXPIRED_TOKEN);
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("유효하지 않은 JWT 토큰입니다: {}", e.getMessage());
            throw new BusinessException(ErrorCode.INVALID_TOKEN);
        }
    }

    public boolean isAccessToken(String token) {
        try {
            Claims claims = parseClaims(token);
            return ACCESS_TOKEN_TYPE.equals(claims.get(TOKEN_TYPE_KEY));
        } catch (Exception e) {
            return false;
        }
    }

    public AccessTokenInfo parseAccessToken(String token) {
        Claims claims = parseClaims(token);
        String userId = claims.getSubject();

        return new AccessTokenInfo(userId);
    }

    public long getRemainingExpiration(String token) {
        try {
            Date expiration = parseClaims(token).getExpiration();
            long now = System.currentTimeMillis();
            return Math.max(expiration.getTime() - now, 0);
        } catch (Exception e) {
            return 0;
        }
    }

    public Long getAccessTokenExpirationSeconds() {
        return this.accessTokenExpirationSeconds;
    }

    public Long getRefreshTokenExpirationSeconds() {
        return this.refreshTokenExpirationSeconds;
    }
}