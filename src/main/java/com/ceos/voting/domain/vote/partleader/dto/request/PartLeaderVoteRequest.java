package com.ceos.voting.domain.vote.partleader.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PartLeaderVoteRequest(
        @NotBlank String name
) {
}