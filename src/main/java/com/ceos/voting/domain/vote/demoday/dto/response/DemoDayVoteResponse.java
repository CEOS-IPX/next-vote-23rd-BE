package com.ceos.voting.domain.vote.demoday.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "데모데이 투표 응답")
public record DemoDayVoteResponse(
        @Schema(description = "이 투표로 20표가 채워져 마감되었는지 여부", example = "false")
        boolean closed
) {

    public static DemoDayVoteResponse of(boolean closed) {

        return new DemoDayVoteResponse(closed);
    }
}