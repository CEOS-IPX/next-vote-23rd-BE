package com.ceos.voting.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "회원가입 요청")
public record SignupRequest(
        @Schema(description = "로그인 ID", example = "user123")
        @NotBlank(message = "아이디는 필수입니다.")
        String username,

        @Schema(description = "비밀번호", example = "password!@#")
        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,

        @Schema(description = "비밀번호 재입력", example = "password!@#")
        @NotBlank(message = "비밀번호 재입력은 필수입니다.")
        String passwordConfirm,

        @Schema(description = "이메일", example = "user@example.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @Schema(description = "본인 이름", example = "김태익")
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @Schema(description = "본인 파트 (FRONTEND / BACKEND)", example = "FRONTEND")
        @NotBlank(message = "파트는 필수입니다.")
        String part,

        @Schema(description = "본인 팀 (IPX / CONX / GROUPEAT / DITDA / JOBDRI)", example = "IPX")
        @NotBlank(message = "팀은 필수입니다.")
        String team
) {
}