package com.ceos.voting.domain.vote.partleader.service;

import com.ceos.voting.domain.candidate.domain.Candidate;
import com.ceos.voting.domain.vote.demoday.domain.PartLeaderBallot;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderRankingResponse;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderResultResponse;
import com.ceos.voting.domain.vote.partleader.repository.PartLeaderBallotRepository;

import com.ceos.voting.global.common.Part;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartLeaderResultService {

    private static final int CLOSED_VOTE_COUNT = 20;

    private final PartLeaderBallotRepository partLeaderBallotRepository;

    public PartLeaderResultResponse getPartLeaderResult(String partValue) {
        Part part = parsePart(partValue);

        List<Candidate> partCandidates = Arrays.stream(Candidate.values())
                .filter(candidate -> candidate.getPart() == part)
                .toList();

        List<PartLeaderBallot> ballots = partLeaderBallotRepository.findAllByCandidateIn(partCandidates);

        Map<Candidate, Long> voteCountMap = ballots.stream()
                .collect(Collectors.groupingBy(
                        PartLeaderBallot::getCandidate,
                        Collectors.counting()
                ));

        int totalVotes = voteCountMap.values().stream()
                .mapToInt(Long::intValue)
                .sum();

        List<CandidateVoteCount> sortedResults = partCandidates.stream()
                .map(candidate -> new CandidateVoteCount(
                        candidate,
                        voteCountMap.getOrDefault(candidate, 0L).intValue()
                ))
                .sorted(
                        Comparator.comparingInt(CandidateVoteCount::votes).reversed()
                                .thenComparing(result -> result.candidate().getDisplayName())
                )
                .toList();

        List<PartLeaderRankingResponse> rankings = createRankings(sortedResults);

        return PartLeaderResultResponse.of(
                totalVotes >= CLOSED_VOTE_COUNT,
                totalVotes,
                part.name(),
                rankings
        );
    }

    private Part parsePart(String partValue) {
        if (partValue == null || partValue.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }

        try {
            return Part.valueOf(partValue);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    private List<PartLeaderRankingResponse> createRankings(List<CandidateVoteCount> sortedResults) {
        return java.util.stream.IntStream.range(0, sortedResults.size())
                .mapToObj(index -> {
                    CandidateVoteCount result = sortedResults.get(index);

                    return PartLeaderRankingResponse.of(
                            index + 1,
                            result.candidate().getDisplayName(),
                            result.votes()
                    );
                })
                .toList();
    }

    private record CandidateVoteCount(
            Candidate candidate,
            Integer votes
    ) {
    }
}