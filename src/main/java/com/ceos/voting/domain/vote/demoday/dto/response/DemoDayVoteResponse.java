package com.ceos.voting.domain.vote.demoday.dto.response;

public record DemoDayVoteResponse(
        boolean closed
) {

    public static DemoDayVoteResponse of(boolean closed) {

        return new DemoDayVoteResponse(closed);
    }
}