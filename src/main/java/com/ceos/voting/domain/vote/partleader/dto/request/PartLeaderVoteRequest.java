package com.ceos.voting.domain.vote.partleader.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "파트장 투표 요청")
public record PartLeaderVoteRequest(
        @Schema(description = "투표할 후보의 이름", example = "김태익")
        @NotBlank String name
) {
}