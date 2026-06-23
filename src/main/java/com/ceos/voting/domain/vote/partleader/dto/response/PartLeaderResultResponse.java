package com.ceos.voting.domain.vote.partleader.dto.response;

import java.util.List;

public record PartLeaderResultResponse(
        Boolean closed,
        Integer totalVotes,
        String part,
        List<PartLeaderRankingResponse> rankings
) {
    public static PartLeaderResultResponse of(
            Boolean closed,
            Integer totalVotes,
            String part,
            List<PartLeaderRankingResponse> rankings
    ) {
        return new PartLeaderResultResponse(closed, totalVotes, part, rankings);
    }
}