package com.ceos.voting.domain.candidate.controller;

import com.ceos.voting.domain.candidate.dto.response.CandidateResponse;
import com.ceos.voting.domain.candidate.service.CandidateService;
import com.ceos.voting.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Candidate", description = "후보 목록 조회 API (회원가입 드롭다운용)")
@RestController
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @Operation(summary = "후보 목록 조회", description = "파트·팀 필터로 회원가입 드롭다운용 후보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 파트/팀 값 (C001)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
    @SecurityRequirements({})
    @GetMapping("/api/candidates")
    public ResponseEntity<ApiResponse<List<CandidateResponse>>> getCandidates(
            @Parameter(description = "파트 필터 (FRONTEND / BACKEND)", example = "FRONTEND")
            @RequestParam(required = false) String part,
            @Parameter(description = "팀 필터 (IPX / CONX / GROUPEAT / DITDA / JOBDRI)", example = "IPX")
            @RequestParam(required = false) String team
    ) {
        List<CandidateResponse> response = candidateService.getCandidates(part, team);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}