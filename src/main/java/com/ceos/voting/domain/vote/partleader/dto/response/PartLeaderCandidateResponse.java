package com.ceos.voting.domain.vote.partleader.dto.response;

import com.ceos.voting.domain.candidate.domain.Candidate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "파트장 후보 정보")
public record PartLeaderCandidateResponse(
        @Schema(description = "후보 이름", example = "김태익")
        String name
) {
    public static PartLeaderCandidateResponse from(Candidate candidate) {
        return new PartLeaderCandidateResponse(candidate.getDisplayName());
    }
}