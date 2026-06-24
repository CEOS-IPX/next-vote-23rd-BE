package com.ceos.voting.domain.vote.demoday.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "데모데이 투표 결과 응답")
public record DemoDayResultResponse(
        @Schema(description = "마감 여부 (20표 도달 시 true)", example = "false")
        Boolean closed,

        @Schema(description = "현재까지 모인 총 득표수", example = "15")
        Integer totalVotes,

        @Schema(description = "순위 목록 (득표수 내림차순, 5개 팀 전체 포함)")
        List<DemoDayRankingResponse> rankings
) {
    public static DemoDayResultResponse of(
            Boolean closed,
            Integer totalVotes,
            List<DemoDayRankingResponse> rankings
    ) {
        return new DemoDayResultResponse(closed, totalVotes, rankings);
    }
}