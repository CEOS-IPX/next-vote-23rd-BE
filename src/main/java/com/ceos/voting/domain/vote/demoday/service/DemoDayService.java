package com.ceos.voting.domain.vote.demoday.service;

import com.ceos.voting.domain.user.domain.User;
import com.ceos.voting.domain.user.repository.UserRepository;
import com.ceos.voting.domain.vote.demoday.dto.request.DemoDayVoteRequest;
import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayCandidateResponse;
import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayCandidatesResponse;
import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayRankingResponse;
import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayResultResponse;
import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayVoteResponse;
import com.ceos.voting.domain.vote.demoday.repository.DemoDayBallotRepository;
import com.ceos.voting.domain.vote.demoday.repository.DemoDayParticipationRepository;
import com.ceos.voting.domain.vote.partleader.domain.DemoDayBallot;
import com.ceos.voting.domain.vote.partleader.domain.DemoDayParticipation;
import com.ceos.voting.global.common.Team;
import com.ceos.voting.global.exception.BusinessException;
import com.ceos.voting.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DemoDayService {

    private static final int CLOSED_VOTE_COUNT = 20;

    private final UserRepository userRepository;
    private final DemoDayBallotRepository demoDayBallotRepository;
    private final DemoDayParticipationRepository demoDayParticipationRepository;

    @Transactional
    public DemoDayVoteResponse castVote(Long voterId, DemoDayVoteRequest request) {
        User voter = userRepository.findById(voterId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Team team = parseTeam(request.team());

        if (team == voter.getTeam()) {
            throw new BusinessException(ErrorCode.OWN_TEAM_NOT_ALLOWED);
        }

        long currentCount = demoDayBallotRepository.count();
        if (currentCount >= CLOSED_VOTE_COUNT) {
            throw new BusinessException(ErrorCode.VOTING_CLOSED);
        }

        if (demoDayParticipationRepository.existsByUserId(voterId)) {
            throw new BusinessException(ErrorCode.DUPLICATED_VOTE);
        }

        demoDayParticipationRepository.save(new DemoDayParticipation(voterId));
        demoDayBallotRepository.save(new DemoDayBallot(team));

        boolean closed = (currentCount + 1) >= CLOSED_VOTE_COUNT;
        return DemoDayVoteResponse.of(closed);
    }

    private Team parseTeam(String teamValue) {
        try {
            return Team.valueOf(teamValue);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_TEAM);
        }
    }

    public DemoDayCandidatesResponse getDemoDayCandidates() {
        List<DemoDayCandidateResponse> candidates = Arrays.stream(Team.values())
                .map(DemoDayCandidateResponse::from)
                .toList();

        return DemoDayCandidatesResponse.of(candidates);
    }

    public DemoDayResultResponse getDemoDayResult() {
        List<Team> teams = Arrays.asList(Team.values());

        List<DemoDayBallot> ballots = demoDayBallotRepository.findAllByTeamIn(teams);

        Map<Team, Long> voteCountMap = ballots.stream()
                .collect(Collectors.groupingBy(
                        DemoDayBallot::getTeam,
                        Collectors.counting()
                ));

        int totalVotes = voteCountMap.values().stream()
                .mapToInt(Long::intValue)
                .sum();

        List<TeamVoteCount> sortedResults = teams.stream()
                .map(team -> new TeamVoteCount(
                        team,
                        voteCountMap.getOrDefault(team, 0L).intValue()
                ))
                .sorted(
                        Comparator.comparingInt(TeamVoteCount::votes).reversed()
                                .thenComparing(result -> result.team().name())
                )
                .toList();

        List<DemoDayRankingResponse> rankings = createRankings(sortedResults);

        return DemoDayResultResponse.of(
                totalVotes >= CLOSED_VOTE_COUNT,
                totalVotes,
                rankings
        );
    }

    private List<DemoDayRankingResponse> createRankings(List<TeamVoteCount> sortedResults) {
        return IntStream.range(0, sortedResults.size())
                .mapToObj(index -> {
                    TeamVoteCount result = sortedResults.get(index);

                    return DemoDayRankingResponse.of(
                            index + 1,
                            result.team().name(),
                            result.votes()
                    );
                })
                .toList();
    }

    private record TeamVoteCount(
            Team team,
            Integer votes
    ) {
    }
}