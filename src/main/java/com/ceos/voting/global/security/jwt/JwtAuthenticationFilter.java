package com.ceos.voting.global.security.jwt;

import com.ceos.voting.global.exception.BusinessException;
import com.ceos.voting.global.exception.ErrorCode;
import com.ceos.voting.global.response.ApiResponse;
import com.ceos.voting.global.response.ErrorResponse;
import com.ceos.voting.global.security.dto.AccessTokenInfo;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    public static final String BEARER = "Bearer ";
    public static final String BLACKLIST_PREFIX = "BLACKLIST:";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = resolveToken(request);

        if (StringUtils.hasText(token)) {
            try {
                String logout = redisTemplate.opsForValue().get(BLACKLIST_PREFIX + token);
                if (StringUtils.hasText(logout)) {
                    log.warn("이미 로그아웃 처리된 토큰입니다.");
                    sendErrorResponse(response, ErrorCode.LOGOUT_TOKEN);
                    return;
                }

                AccessTokenInfo accessTokenInfo = tokenProvider.parseAccessToken(token);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        Long.parseLong(accessTokenInfo.userId()), null, List.of());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (BusinessException e) {
                log.warn("JWT 인증 실패: {}", e.getErrorCode().getMessage());
                sendErrorResponse(response, e.getErrorCode());
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
            return bearerToken.substring(BEARER.length());
        }
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = ApiResponse.fail(errorCode);

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }
}