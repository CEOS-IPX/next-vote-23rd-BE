package com.ceos.voting.domain.vote.partleader.service;

import com.ceos.voting.domain.candidate.domain.Candidate;
import com.ceos.voting.domain.user.domain.User;
import com.ceos.voting.domain.user.repository.UserRepository;
import com.ceos.voting.domain.vote.partleader.domain.PartLeaderBallot;
import com.ceos.voting.domain.vote.partleader.domain.PartLeaderParticipation;
import com.ceos.voting.domain.vote.partleader.dto.request.PartLeaderVoteRequest;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderCandidateResponse;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderCandidatesResponse;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderRankingResponse;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderResultResponse;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderVoteResponse;
import com.ceos.voting.domain.vote.partleader.repository.PartLeaderBallotRepository;
import com.ceos.voting.domain.vote.partleader.repository.PartLeaderParticipationRepository;
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
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PartLeaderService {

    private static final int CLOSED_VOTE_COUNT = 20;

    private final UserRepository userRepository;
    private final PartLeaderBallotRepository partLeaderBallotRepository;
    private final PartLeaderParticipationRepository partLeaderParticipationRepository;

    public PartLeaderCandidatesResponse getPartLeaderCandidates(String partValue) {
        Part part = parsePart(partValue);

        List<PartLeaderCandidateResponse> candidates = Arrays.stream(Candidate.values())
                .filter(candidate -> candidate.getPart() == part)
                .map(PartLeaderCandidateResponse::from)
                .toList();

        return PartLeaderCandidatesResponse.of(part.name(), candidates);
    }

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

    @Transactional
    public PartLeaderVoteResponse castVote(Long voterId, PartLeaderVoteRequest request) {
        User voter = userRepository.findById(voterId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Candidate candidate = Candidate.findByName(request.name());

        if (candidate.getPart() != voter.getPart()) {
            throw new BusinessException(ErrorCode.WRONG_PART);
        }

        if (candidate == voter.getCandidate()) {
            throw new BusinessException(ErrorCode.SELF_VOTE_NOT_ALLOWED);
        }

        List<Candidate> partCandidates = Arrays.stream(Candidate.values())
                .filter(c -> c.getPart() == voter.getPart())
                .toList();

        long currentCount = partLeaderBallotRepository.countByCandidateIn(partCandidates);
        if (currentCount >= CLOSED_VOTE_COUNT) {
            throw new BusinessException(ErrorCode.VOTING_CLOSED);
        }

        if (partLeaderParticipationRepository.existsByUserId(voterId)) {
            throw new BusinessException(ErrorCode.DUPLICATED_VOTE);
        }

        partLeaderParticipationRepository.save(new PartLeaderParticipation(voterId));
        partLeaderBallotRepository.save(new PartLeaderBallot(candidate));

        boolean closed = (currentCount + 1) >= CLOSED_VOTE_COUNT;
        return PartLeaderVoteResponse.of(closed);
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
        return IntStream.range(0, sortedResults.size())
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