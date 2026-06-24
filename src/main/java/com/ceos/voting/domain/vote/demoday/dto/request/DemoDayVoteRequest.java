package com.ceos.voting.domain.vote.demoday.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DemoDayVoteRequest(
        @NotBlank String team
) {
}
