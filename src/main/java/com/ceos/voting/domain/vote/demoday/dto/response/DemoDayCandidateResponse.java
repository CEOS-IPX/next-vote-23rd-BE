package com.ceos.voting.domain.vote.demoday.dto.response;

import com.ceos.voting.global.common.Team;

public record DemoDayCandidateResponse(
        String team
) {
    public static DemoDayCandidateResponse from(Team team) {
        return new DemoDayCandidateResponse(team.name());
    }
}