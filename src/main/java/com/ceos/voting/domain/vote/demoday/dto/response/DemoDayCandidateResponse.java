package com.ceos.voting.domain.vote.demoday.dto.response;

import com.ceos.voting.global.common.Team;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "데모데이 후보 팀 정보")
public record DemoDayCandidateResponse(
        @Schema(description = "팀 식별자", example = "IPX")
        String team
) {
    public static DemoDayCandidateResponse from(Team team) {
        return new DemoDayCandidateResponse(team.name());
    }
}