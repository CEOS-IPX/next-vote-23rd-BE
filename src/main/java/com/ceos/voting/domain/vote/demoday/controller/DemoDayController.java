package com.ceos.voting.domain.vote.demoday.controller;

import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayCandidatesResponse;
import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayResultResponse;
import com.ceos.voting.domain.vote.demoday.service.DemoDayService;
import com.ceos.voting.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes/demo-day")
public class DemoDayController {

    private final DemoDayService demoDayService;

    @GetMapping("/candidates")
    public ResponseEntity<ApiResponse<DemoDayCandidatesResponse>> getDemoDayCandidates() {
        DemoDayCandidatesResponse response = demoDayService.getDemoDayCandidates();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/result")
    public ResponseEntity<ApiResponse<DemoDayResultResponse>> getDemoDayResult() {
        DemoDayResultResponse response = demoDayService.getDemoDayResult();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}