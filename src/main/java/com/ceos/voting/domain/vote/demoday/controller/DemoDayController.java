package com.ceos.voting.domain.vote.demoday.controller;

import com.ceos.voting.domain.vote.demoday.dto.request.DemoDayVoteRequest;
import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayCandidatesResponse;
import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayResultResponse;
import com.ceos.voting.domain.vote.demoday.dto.response.DemoDayVoteResponse;
import com.ceos.voting.domain.vote.demoday.service.DemoDayService;
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

@Tag(name = "DemoDay Vote", description = "데모데이 투표 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/votes/demo-day")
public class DemoDayController {

    private final DemoDayService demoDayService;

    @Operation(summary = "데모데이 투표", description = "5개 팀 중 하나에 1표를 행사합니다. 본인 팀 제외, 1인 1표.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "투표 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 팀 (V001) / 본인 팀 투표 (V004)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "인증 필요 (C002)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "중복 투표 (V005)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "410", description = "투표 마감 (V006)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
    @PostMapping
    public ResponseEntity<ApiResponse<DemoDayVoteResponse>> castVote(
            @Parameter(hidden = true) @AuthenticationPrincipal Long userId,
            @RequestBody @Valid DemoDayVoteRequest request
    ) {
        DemoDayVoteResponse response = demoDayService.castVote(userId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(response));
    }

    @Operation(summary = "데모데이 후보 조회", description = "데모데이 후보 5개 팀을 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
    @SecurityRequirements({})
    @GetMapping("/candidates")
    public ResponseEntity<ApiResponse<DemoDayCandidatesResponse>> getDemoDayCandidates() {
        DemoDayCandidatesResponse response = demoDayService.getDemoDayCandidates();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }

    @Operation(summary = "데모데이 투표 결과 조회", description = "데모데이 투표 결과를 득표수 내림차순으로 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공 (진행 중/마감 동일)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
    @SecurityRequirements({})
    @GetMapping("/result")
    public ResponseEntity<ApiResponse<DemoDayResultResponse>> getDemoDayResult() {
        DemoDayResultResponse response = demoDayService.getDemoDayResult();
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}