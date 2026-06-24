package com.ceos.voting.domain.vote.partleader.controller;

import com.ceos.voting.domain.vote.partleader.dto.request.PartLeaderVoteRequest;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderCandidatesResponse;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderResultResponse;
import com.ceos.voting.domain.vote.partleader.dto.response.PartLeaderVoteResponse;
import com.ceos.voting.domain.vote.partleader.service.PartLeaderService;
import com.ceos.voting.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PartLeader Vote", description = "파트장 투표 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes/part-leader")
public class PartLeaderController {

    private final PartLeaderService partLeaderService;

    @Operation(summary = "파트장 투표", description = "본인 파트 후보에게 1표를 행사합니다. 본인 제외, 1인 1표.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "투표 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 후보 (U002) / 다른 파트 후보 (V002) / 본인 투표 (V003)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요 (C002)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "중복 투표 (V005)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "410", description = "투표 마감 (V006)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<PartLeaderVoteResponse>> castVote(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody @Valid PartLeaderVoteRequest request
    ) {
        PartLeaderVoteResponse response = partLeaderService.castVote(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(response));
    }

    @Operation(summary = "파트장 후보 조회", description = "지정한 파트의 후보 목록을 가나다순으로 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 파트 값 (C001)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
    @SecurityRequirements({})
    @GetMapping("/candidates")
    public ResponseEntity<ApiResponse<PartLeaderCandidatesResponse>> getPartLeaderCandidates(
            @Parameter(description = "조회할 파트 (FRONTEND / BACKEND)", example = "FRONTEND", required = true)
            @RequestParam(required = false) String part
    ) {
        PartLeaderCandidatesResponse response = partLeaderService.getPartLeaderCandidates(part);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @Operation(summary = "파트장 투표 결과 조회", description = "지정한 파트의 투표 결과를 득표수 내림차순으로 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공 (진행 중/마감 동일)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 파트 값 (C001)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
    @SecurityRequirements({})
    @GetMapping("/result")
    public ResponseEntity<ApiResponse<PartLeaderResultResponse>> getPartLeaderResult(
            @Parameter(description = "조회할 파트 (FRONTEND / BACKEND)", example = "FRONTEND", required = true)
            @RequestParam(required = false) String part
    ) {
        PartLeaderResultResponse response = partLeaderService.getPartLeaderResult(part);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}