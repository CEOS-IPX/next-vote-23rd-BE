package com.ceos.voting.domain.member.service;

import com.ceos.voting.domain.candidate.domain.Candidate;
import com.ceos.voting.domain.member.dto.response.MemberResponse;
import com.ceos.voting.global.common.Part;
import com.ceos.voting.global.exception.BusinessException;
import com.ceos.voting.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MemberService {

    public List<MemberResponse> getMembers(String partValue) {
        Part part = parsePart(partValue);

        return Arrays.stream(Candidate.values())
                .filter(candidate -> candidate.getPart() == part)
                .map(MemberResponse::from)
                .toList();
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