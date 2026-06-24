package com.ceos.voting.domain.vote.partleader.dto.response;

import com.ceos.voting.domain.candidate.domain.Candidate;

public record PartLeaderCandidateResponse(
        String name
) {
    public static PartLeaderCandidateResponse from(Candidate candidate) {
        return new PartLeaderCandidateResponse(candidate.getDisplayName());
    }
}