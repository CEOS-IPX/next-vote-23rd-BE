package com.ceos.voting.domain.vote.demoday.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "데모데이 투표 순위")
public record DemoDayRankingResponse(
        @Schema(description = "순위", example = "1")
        Integer rank,

        @Schema(description = "팀 식별자", example = "CONX")
        String team,

        @Schema(description = "득표수", example = "7")
        Integer votes
) {
    public static DemoDayRankingResponse of(Integer rank, String team, Integer votes) {
        return new DemoDayRankingResponse(rank, team, votes);
    }
}