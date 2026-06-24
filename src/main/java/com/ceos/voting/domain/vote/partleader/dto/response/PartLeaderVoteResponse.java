package com.ceos.voting.domain.vote.partleader.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "파트장 투표 응답")
public record PartLeaderVoteResponse(
        @Schema(description = "이 투표로 20표가 채워져 마감되었는지 여부", example = "false")
        boolean closed
) {
    public static PartLeaderVoteResponse of(boolean closed) {

        return new PartLeaderVoteResponse(closed);
    }
}