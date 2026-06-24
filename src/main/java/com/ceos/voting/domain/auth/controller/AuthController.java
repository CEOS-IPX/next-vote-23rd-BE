package com.ceos.voting.domain.auth.controller;

import com.ceos.voting.domain.auth.dto.request.LoginRequest;
import com.ceos.voting.domain.auth.dto.response.*;
import com.ceos.voting.domain.auth.dto.request.SignupRequest;
import com.ceos.voting.domain.auth.service.AuthService;
import com.ceos.voting.global.cookie.CookieUtil;
import com.ceos.voting.global.exception.BusinessException;
import com.ceos.voting.global.exception.ErrorCode;
import com.ceos.voting.global.response.ApiResponse;
import com.ceos.voting.global.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "회원가입 및 로그인 인증 API")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;

    public static final String BEARER = "Bearer ";

    @Operation(summary = "회원가입", description = "사용자를 등록합니다.")
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = authService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response));
    }

    @Operation(summary = "로그인", description = "로그인하여 JWT 토큰을 발급받습니다.")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse tokens = authService.login(request);

        ResponseCookie cookie = CookieUtil.createCookie("refreshToken", tokens.refreshToken(), tokenProvider.getRefreshTokenExpirationSeconds());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.ok(
                        LoginResponse.of(tokens.accessToken(), tokens.expiresIn(), tokens.user()))
                );
    }

    @Operation(summary = "토큰 재발급", description = "쿠키의 Refresh Token을 이용해 새로운 토큰 쌍을 발급 받습니다.")
    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<ReissueResponse>> reissue(
            @Parameter(hidden = true)
            @CookieValue(value = "refreshToken", required = false) String refreshToken) {

        if (refreshToken == null) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_MISSING);
        }

        ReissueInfo info = authService.reissue(refreshToken);

        ResponseCookie cookie = CookieUtil.createCookie("refreshToken", info.refreshToken(), tokenProvider.getRefreshTokenExpirationSeconds());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.ok(ReissueResponse.of(info.accessToken(), info.expiresIn())));
    }

    @Operation(summary = "로그아웃", description = "Refresh Token을 삭제하고 쿠키를 비웁니다.")
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(
            HttpServletRequest request,
            @Parameter(hidden = true)
            @CookieValue(value = "refreshToken", required = false) String refreshToken) {

        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = null;

        if (StringUtils.hasText(bearer) && bearer.startsWith(BEARER)) {
            accessToken = bearer.substring(BEARER.length());
        }

        authService.logout(accessToken, refreshToken);

        ResponseCookie deleteCookie = CookieUtil.emptyCookie("refreshToken");

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .body(ApiResponse.ok());
    }
}