package com.ceos.voting.domain.vote.demoday.service;

import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayCandidateResponse;
import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayCandidatesResponse;
import com.ceos.voting.global.common.Team;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DemoDayVoteService {

    public DemoDayCandidatesResponse getDemoDayCandidates() {
        List<DemoDayCandidateResponse> candidates = Arrays.stream(Team.values())
                .map(DemoDayCandidateResponse::from)
                .toList();

        return DemoDayCandidatesResponse.of(candidates);
    }
}