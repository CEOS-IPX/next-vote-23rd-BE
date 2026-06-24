package com.ceos.voting.domain.vote.demoday.dto.response;

public record DemoDayRankingResponse(
        Integer rank,
        String team,
        Integer votes
) {
    public static DemoDayRankingResponse of(Integer rank, String team, Integer votes) {
        return new DemoDayRankingResponse(rank, team, votes);
    }
}