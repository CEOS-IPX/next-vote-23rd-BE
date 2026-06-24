package com.ceos.voting.domain.vote.demoday.dto.response;

import java.util.List;

public record DemoDayResultResponse(
        Boolean closed,
        Integer totalVotes,
        List<DemoDayRankingResponse> rankings
) {
    public static DemoDayResultResponse of(
            Boolean closed,
            Integer totalVotes,
            List<DemoDayRankingResponse> rankings
    ) {
        return new DemoDayResultResponse(closed, totalVotes, rankings);
    }
}