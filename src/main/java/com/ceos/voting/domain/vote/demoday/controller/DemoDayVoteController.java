package com.ceos.voting.domain.vote.demoday.controller;

import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayCandidatesResponse;
import com.ceos.voting.domain.vote.demoday.service.DemoDayVoteService;
import com.ceos.voting.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes/demo-day")
public class DemoDayVoteController {

    private final DemoDayVoteService demoDayVoteService;

    @GetMapping("/candidates")
    public ResponseEntity<ApiResponse<DemoDayCandidatesResponse>> getDemoDayCandidates() {
        DemoDayCandidatesResponse response = demoDayVoteService.getDemoDayCandidates();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}