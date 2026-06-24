package com.ceos.voting.domain.vote.partleader.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "파트장 투표 결과 응답")
public record PartLeaderResultResponse(
        @Schema(description = "마감 여부 (20표 도달 시 true)", example = "false")
        Boolean closed,

        @Schema(description = "현재까지 모인 총 득표수", example = "12")
        Integer totalVotes,

        @Schema(description = "조회한 파트", example = "FRONTEND")
        String part,

        @Schema(description = "순위 목록 (득표수 내림차순)")
        List<PartLeaderRankingResponse> rankings
) {
    public static PartLeaderResultResponse of(
            Boolean closed,
            Integer totalVotes,
            String part,
            List<PartLeaderRankingResponse> rankings
    ) {
        return new PartLeaderResultResponse(closed, totalVotes, part, rankings);
    }
}