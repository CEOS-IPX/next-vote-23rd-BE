package com.ceos.voting.domain.member.dto.response;

import com.ceos.voting.domain.candidate.domain.Candidate;

public record MemberResponse(
        String name,
        String part,
        String team
) {
    public static MemberResponse from(Candidate candidate) {
        return new MemberResponse(
                candidate.getDisplayName(),
                candidate.getPart().name(),
                candidate.getTeam().name()
        );
    }
}