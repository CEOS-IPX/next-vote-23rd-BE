package com.ceos.voting.domain.auth.dto.response;

import com.ceos.voting.domain.user.domain.User;

public record ReissueInfo(
        String accessToken,
        String tokenType,
        Long expiresIn,
        String refreshToken
) {
    public static ReissueInfo of(String accessToken, Long expiresIn, String refreshToken) {
        return new ReissueInfo(
                accessToken,
                "Bearer",
                expiresIn,
                refreshToken
        );
    }
}
