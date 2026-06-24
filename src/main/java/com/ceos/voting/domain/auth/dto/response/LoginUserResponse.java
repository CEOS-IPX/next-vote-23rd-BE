package com.ceos.voting.domain.auth.dto.response;

import com.ceos.voting.domain.user.domain.User;

public record LoginUserResponse(
        Long userId,
        String username,
        String name,
        String part,
        String team
) {
    public static LoginUserResponse from(User user) {
        return new LoginUserResponse(
                user.getId(),
                user.getUsername(),
                user.getDisplayName(),
                user.getPart().name(),
                user.getTeam().name()
        );
    }
}