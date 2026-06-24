package com.ceos.voting.domain.vote.partleader.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "파트장 투표 순위")
public record PartLeaderRankingResponse(
        @Schema(description = "순위", example = "1")
        Integer rank,

        @Schema(description = "후보 이름", example = "김태익")
        String name,

        @Schema(description = "득표수", example = "4")
        Integer votes
) {
    public static PartLeaderRankingResponse of(Integer rank, String name, Integer votes) {
        return new PartLeaderRankingResponse(rank, name, votes);
    }
}