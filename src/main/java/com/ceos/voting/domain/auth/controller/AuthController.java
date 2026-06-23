package com.ceos.voting.domain.auth.controller;

import com.ceos.voting.domain.auth.dto.request.LoginRequest;
import com.ceos.voting.domain.auth.dto.response.LoginResponse;
import com.ceos.voting.domain.auth.service.AuthService;
import com.ceos.voting.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}