package com.ceos.voting.domain.vote.partleader.controller;

import com.ceos.voting.domain.vote.partleader.dto.request.PartLeaderVoteRequest;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderCandidatesResponse;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderResultResponse;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderVoteResponse;
import com.ceos.voting.domain.vote.partleader.service.PartLeaderService;
import com.ceos.voting.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes/part-leader")
public class PartLeaderController {

    private final PartLeaderService partLeaderService;

    @PostMapping
    public ResponseEntity<ApiResponse<PartLeaderVoteResponse>> castVote(
            @AuthenticationPrincipal Long userId,
            @RequestBody @Valid PartLeaderVoteRequest request
    ) {
        PartLeaderVoteResponse response = partLeaderService.castVote(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(response));
    }

    @GetMapping("/candidates")
    public ResponseEntity<ApiResponse<PartLeaderCandidatesResponse>> getPartLeaderCandidates(
            @RequestParam(required = false) String part
    ) {
        PartLeaderCandidatesResponse response = partLeaderService.getPartLeaderCandidates(part);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @GetMapping("/result")
    public ResponseEntity<ApiResponse<PartLeaderResultResponse>> getPartLeaderResult(
            @RequestParam(required = false) String part
    ) {
        PartLeaderResultResponse response = partLeaderService.getPartLeaderResult(part);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}