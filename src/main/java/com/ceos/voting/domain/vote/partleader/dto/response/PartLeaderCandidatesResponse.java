package com.ceos.voting.domain.vote.partleader.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "파트장 후보 목록 응답")
public record PartLeaderCandidatesResponse(
        @Schema(description = "조회한 파트", example = "FRONTEND")
        String part,

        @Schema(description = "후보 목록 (가나다순)")
        List<PartLeaderCandidateResponse> candidates
) {
    public static PartLeaderCandidatesResponse of(
            String part,
            List<PartLeaderCandidateResponse> candidates
    ) {
        return new PartLeaderCandidatesResponse(part, candidates);
    }
}