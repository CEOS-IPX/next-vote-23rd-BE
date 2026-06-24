package com.ceos.voting.domain.auth.dto.response;

import com.ceos.voting.domain.user.domain.User;

public record ReissueResponse(
        String accessToken,
        String tokenType,
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
