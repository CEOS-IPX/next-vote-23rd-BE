package com.ceos.voting.domain.auth.dto.response;

import com.ceos.voting.domain.user.domain.User;

public record SignupResponse(
        Long userId,
        String username,
        String name,
        String part,
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