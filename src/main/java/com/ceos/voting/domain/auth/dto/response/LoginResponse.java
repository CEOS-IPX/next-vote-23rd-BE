package com.ceos.voting.domain.auth.dto.response;

import com.ceos.voting.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "로그인 응답")
public record LoginResponse(
        @Schema(description = "발급된 AccessToken", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken,

        @Schema(description = "토큰 타입", example = "Bearer")
        String tokenType,

        @Schema(description = "AccessToken 만료 시간 (초)", example = "900")
        Long expiresIn,

        @Schema(description = "사용자 정보")
        LoginUserResponse user
) {
    public static LoginResponse of(String accessToken, Long expiresIn, User user) {
        return new LoginResponse(
                accessToken,
                "Bearer",
                expiresIn,
                LoginUserResponse.from(user)
        );
    }
}