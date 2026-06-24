package com.ceos.voting.domain.candidate.controller;

import com.ceos.voting.domain.candidate.dto.response.CandidateResponse;
import com.ceos.voting.domain.candidate.service.CandidateService;
import com.ceos.voting.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/api/candidates")
    public ResponseEntity<ApiResponse<List<CandidateResponse>>> getCandidates(
            @RequestParam(required = false) String part,
            @RequestParam(required = false) String team
    ) {
        List<CandidateResponse> response = candidateService.getCandidates(part, team);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}