package com.ceos.voting.domain.auth.dto.response;

import com.ceos.voting.domain.user.domain.User;

public record TokenResponse(
        String accessToken,
        String tokenType,
        Long expiresIn,
        User user,
        String refreshToken
) {
    public static TokenResponse of(String accessToken, Long expiresIn, User user, String refreshToken) {
        return new TokenResponse(
                accessToken,
                "Bearer",
                expiresIn,
                user,
                refreshToken
        );
    }
}