package com.ceos.voting.domain.vote.partleader.controller;

import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderCandidatesResponse;
import com.ceos.voting.domain.vote.partleader.service.PartLeaderVoteService;
import com.ceos.voting.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes/part-leader")
public class PartLeaderVoteController {

    private final PartLeaderVoteService partLeaderVoteService;

    @GetMapping("/candidates")
    public ResponseEntity<ApiResponse<PartLeaderCandidatesResponse>> getPartLeaderCandidates(
            @RequestParam(required = false) String part
    ) {
        PartLeaderCandidatesResponse response = partLeaderVoteService.getPartLeaderCandidates(part);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}