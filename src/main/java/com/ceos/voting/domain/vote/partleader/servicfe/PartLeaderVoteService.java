package com.ceos.voting.domain.vote.partleader.service;

import com.ceos.voting.domain.candidate.domain.Candidate;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderCandidateResponse;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderCandidatesResponse;
import com.ceos.voting.global.common.Part;
import com.ceos.voting.global.exception.BusinessException;
import com.ceos.voting.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PartLeaderVoteService {

    public PartLeaderCandidatesResponse getPartLeaderCandidates(String partValue) {
        Part part = parsePart(partValue);

        List<PartLeaderCandidateResponse> candidates = Arrays.stream(Candidate.values())
                .filter(candidate -> candidate.getPart() == part)
                .map(PartLeaderCandidateResponse::from)
                .toList();

        return PartLeaderCandidatesResponse.of(part.name(), candidates);
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
}