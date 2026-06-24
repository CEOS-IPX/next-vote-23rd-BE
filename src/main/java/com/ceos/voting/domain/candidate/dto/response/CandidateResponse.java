package com.ceos.voting.domain.candidate.dto.response;

import com.ceos.voting.domain.candidate.domain.Candidate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "후보 정보")
public record CandidateResponse(
        @Schema(description = "후보 이름", example = "김태익")
        String name,

        @Schema(description = "파트", example = "FRONTEND")
        String part,

        @Schema(description = "팀", example = "IPX")
        String team
) {
    public static CandidateResponse from(Candidate candidate) {
        return new CandidateResponse(
                candidate.getDisplayName(),
                candidate.getPart().name(),
                candidate.getTeam().name()
        );
    }
}