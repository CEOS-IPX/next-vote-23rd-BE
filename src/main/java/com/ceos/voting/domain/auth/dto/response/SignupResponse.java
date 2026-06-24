package com.ceos.voting.domain.auth.dto.response;

import com.ceos.voting.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답")
public record SignupResponse(
        @Schema(description = "사용자 PK", example = "1")
        Long userId,

        @Schema(description = "로그인 ID", example = "user123")
        String username,

        @Schema(description = "이름", example = "김태익")
        String name,

        @Schema(description = "파트", example = "FRONTEND")
        String part,

        @Schema(description = "팀", example = "IPX")
        String team
) {
    public static SignupResponse from(User user) {
        return new SignupResponse(
                user.getId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getPart().name(),
                user.getTeam().name()
        );
    }
}