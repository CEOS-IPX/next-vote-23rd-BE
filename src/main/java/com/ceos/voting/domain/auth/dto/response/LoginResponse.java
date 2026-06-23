package com.ceos.voting.domain.auth.dto.response;

import com.ceos.voting.domain.user.domain.User;

public record LoginResponse(
        String accessToken,
        String tokenType,
        Long expiresIn,
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