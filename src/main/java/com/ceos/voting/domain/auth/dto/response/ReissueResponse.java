package com.ceos.voting.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "AccessToken 재발급 응답")
public record ReissueResponse(
        @Schema(description = "새로 발급된 AccessToken", example = "eyJhbGciOiJIUzI1NiJ9...")
        String accessToken,

        @Schema(description = "토큰 타입", example = "Bearer")
        String tokenType,

        @Schema(description = "AccessToken 만료 시간 (초)", example = "900")
        Long expiresIn
) {
    public static ReissueResponse of(String accessToken, Long expiresIn) {
        return new ReissueResponse(
                accessToken,
                "Bearer",
                expiresIn
        );
    }
}