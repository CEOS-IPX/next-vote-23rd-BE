package com.ceos.voting.domain.candidate.dto.response;

import com.ceos.voting.domain.candidate.domain.Candidate;

public record CandidateResponse(
        String name,
        String part,
        String team
) {
    public static CandidateResponse from(Candidate candidate) {
        return new CandidateResponse(
                candidate.getDisplayName(),
                candidate.getPart().name(),
                candidate.getTeam().name()
        );
    }
}