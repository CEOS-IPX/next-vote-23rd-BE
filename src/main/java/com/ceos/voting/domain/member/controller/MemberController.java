package com.ceos.voting.domain.member.controller;

import com.ceos.voting.domain.member.dto.response.MemberResponse;
import com.ceos.voting.domain.member.service.MemberService;
import com.ceos.voting.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Member", description = "23기 멤버 목록 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "멤버 목록 조회", description = "23기 멤버 정보(이름/파트/팀)를 조회합니다. part 필수.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 파트 값 (C001)"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "서버 내부 오류 (C003)")
    })
    @SecurityRequirements({})
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getMembers(
            @Parameter(description = "파트 필터 (FRONTEND / BACKEND)", example = "FRONTEND", required = true)
            @RequestParam(required = false) String part
    ) {
        List<MemberResponse> response = memberService.getMembers(part);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}