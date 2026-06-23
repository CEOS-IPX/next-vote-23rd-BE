package com.ceos.voting.domain.candidate.service;

import com.ceos.voting.domain.candidate.domain.Candidate;
import com.ceos.voting.domain.candidate.dto.response.CandidateResponse;
import com.ceos.voting.global.common.Part;
import com.ceos.voting.global.common.Team;
import com.ceos.voting.global.exception.BusinessException;
import com.ceos.voting.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CandidateService {

    public List<CandidateResponse> getCandidates(String partValue, String teamValue) {
        Part part = parsePart(partValue);
        Team team = parseTeam(teamValue);

        return Arrays.stream(Candidate.values())
                .filter(candidate -> part == null || candidate.getPart() == part)
                .filter(candidate -> team == null || candidate.getTeam() == team)
                .map(CandidateResponse::from)
                .toList();
    }

    private Part parsePart(String partValue) {
        if (partValue == null) {
            return null;
        }

        try {
            return Part.valueOf(partValue);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    private Team parseTeam(String teamValue) {
        if (teamValue == null) {
            return null;
        }

        try {
            return Team.valueOf(teamValue);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
}