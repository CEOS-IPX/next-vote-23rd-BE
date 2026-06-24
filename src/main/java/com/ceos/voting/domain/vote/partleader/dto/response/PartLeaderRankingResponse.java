package com.ceos.voting.domain.vote.partleader.dto.response;

public record PartLeaderRankingResponse(
        Integer rank,
        String name,
        Integer votes
) {
    public static PartLeaderRankingResponse of(Integer rank, String name, Integer votes) {
        return new PartLeaderRankingResponse(rank, name, votes);
    }
}