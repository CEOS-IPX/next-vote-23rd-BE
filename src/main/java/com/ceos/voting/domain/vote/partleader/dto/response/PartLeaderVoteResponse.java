package com.ceos.voting.domain.vote.partleader.dto.response;

public record PartLeaderVoteResponse(
        boolean closed
) {
    public static PartLeaderVoteResponse of(boolean closed) {

        return new PartLeaderVoteResponse(closed);
    }
}