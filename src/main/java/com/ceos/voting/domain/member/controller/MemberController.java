package com.ceos.voting.domain.member.controller;

import com.ceos.voting.domain.member.dto.response.MemberResponse;
import com.ceos.voting.domain.member.service.MemberService;
import com.ceos.voting.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponse>>> getMembers(
            @RequestParam(required = false) String part
    ) {
        List<MemberResponse> response = memberService.getMembers(part);
        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}