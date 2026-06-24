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
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
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

    @Operation(summary = "회원가입", description = "23기 멤버 중 본인을 선택하여 가입합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "회원가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 오류 (C001) / 비밀번호 불일치 (U001) / 잘못된 후보 (U002)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "아이디 중복 (U003) / 이메일 중복 (U004) / 후보 중복 (U005)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
    @SecurityRequirements({})
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = authService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response));
    }

    @Operation(summary = "로그인", description = "아이디/비밀번호 인증 후 AccessToken을 반환하고 RefreshToken을 쿠키에 설정합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그인 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "입력값 오류 (C001)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "존재하지 않는 아이디 (A001) / 비밀번호 불일치 (A002)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
    @SecurityRequirements({})
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse tokens = authService.login(request);

        ResponseCookie cookie = CookieUtil.createCookie("refreshToken", tokens.refreshToken(), tokens.rtExpiresIn());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.ok(
                        LoginResponse.of(tokens.accessToken(), tokens.expiresIn(), tokens.user()))
                );
    }

    @Operation(summary = "AccessToken 재발급", description = "쿠키의 RefreshToken으로 새 AccessToken을 발급합니다. RTR 적용.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "재발급 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "RT 없음 (A003) / RT 유효하지 않음 (A004)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
    @SecurityRequirements({})
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

    @Operation(summary = "로그아웃", description = "Redis에서 RefreshToken을 삭제하고 쿠키를 만료시킵니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요 (C002)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
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