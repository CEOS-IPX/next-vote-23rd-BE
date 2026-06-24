package com.ceos.voting.domain.vote.demoday.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "데모데이 투표 요청")
public record DemoDayVoteRequest(
        @Schema(description = "투표할 팀 식별자 (IPX / CONX / GROUPEAT / DITDA / JOBDRI)", example = "IPX")
        @NotBlank String team
) {
}