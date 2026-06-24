package com.ceos.voting.domain.vote.demoday.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "데모데이 후보 목록 응답")
public record DemoDayCandidatesResponse(
        @Schema(description = "후보 팀 목록 (5개 팀)")
        List<DemoDayCandidateResponse> candidates
) {
    public static DemoDayCandidatesResponse of(List<DemoDayCandidateResponse> candidates) {
        return new DemoDayCandidatesResponse(candidates);
    }
}