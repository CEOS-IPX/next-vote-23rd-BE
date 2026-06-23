package com.ceos.voting.domain.vote.partleader.controller;

import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderResultResponse;
import com.ceos.voting.domain.vote.partleader.service.PartLeaderResultService;
import com.ceos.voting.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes/part-leader")
public class PartLeaderResultController {

    private final PartLeaderResultService partLeaderResultService;

    @GetMapping("/result")
    public ResponseEntity<ApiResponse<PartLeaderResultResponse>> getPartLeaderResult(
            @RequestParam(required = false) String part
    ) {
        PartLeaderResultResponse response = partLeaderResultService.getPartLeaderResult(part);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}