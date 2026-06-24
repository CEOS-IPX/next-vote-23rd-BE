package com.ceos.voting.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그인 요청")
public record LoginRequest(
        @Schema(description = "로그인 ID", example = "user123")
        @NotBlank(message = "아이디는 필수입니다.")
        String username,

        @Schema(description = "비밀번호", example = "password!@#")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,

        @Schema(description = "로그인 유지 여부", example = "false", nullable = true)
        Boolean rememberMe
) {
    public boolean isRememberMe() {
        return Boolean.TRUE.equals(rememberMe);
    }
}