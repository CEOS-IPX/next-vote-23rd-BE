package com.ceos.voting.domain.vote.demoday.controller;

import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayResultResponse;
import com.ceos.voting.domain.vote.demoday.service.DemoDayResultService;
import com.ceos.voting.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes/demo-day")
public class DemoDayResultController {

    private final DemoDayResultService demoDayResultService;

    @GetMapping("/result")
    public ResponseEntity<ApiResponse<DemoDayResultResponse>> getDemoDayResult() {
        DemoDayResultResponse response = demoDayResultService.getDemoDayResult();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}