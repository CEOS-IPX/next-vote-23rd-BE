package com.ceos.voting.domain.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SignupRequest(
        @NotBlank(message = "아이디는 필수입니다.")
        String username,

        @NotBlank(message = "비밀번호는 필수입니다.")
        String password,

        @NotBlank(message = "비밀번호 재입력은 필수입니다.")
        String passwordConfirm,

        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @NotBlank(message = "파트는 필수입니다.")
        String part,

        @NotBlank(message = "팀은 필수입니다.")
        String team
) {
}