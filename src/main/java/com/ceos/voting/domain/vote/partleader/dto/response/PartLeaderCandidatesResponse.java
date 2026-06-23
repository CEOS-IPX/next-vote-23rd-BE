package com.ceos.voting.domain.vote.partleader.dto.response;

import java.util.List;

public record PartLeaderCandidatesResponse(
        String part,
        List<PartLeaderCandidateResponse> candidates
) {
    public static PartLeaderCandidatesResponse of(
            String part,
            List<PartLeaderCandidateResponse> candidates
    ) {
        return new PartLeaderCandidatesResponse(part, candidates);
    }
}