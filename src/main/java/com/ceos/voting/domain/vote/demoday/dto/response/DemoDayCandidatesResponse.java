package com.ceos.voting.domain.vote.demoday.dto.response;

import java.util.List;

public record DemoDayCandidatesResponse(
        List<DemoDayCandidateResponse> candidates
) {
    public static DemoDayCandidatesResponse of(List<DemoDayCandidateResponse> candidates) {
        return new DemoDayCandidatesResponse(candidates);
    }
}