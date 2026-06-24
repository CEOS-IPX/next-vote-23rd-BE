package com.ceos.voting.domain.member.dto.response;

import com.ceos.voting.domain.candidate.domain.Candidate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "멤버 정보")
public record MemberResponse(
        @Schema(description = "이름", example = "김태익")
        String name,

        @Schema(description = "파트", example = "FRONTEND")
        String part,

        @Schema(description = "팀", example = "IPX")
        String team
) {
    public static MemberResponse from(Candidate candidate) {
        return new MemberResponse(
                candidate.getDisplayName(),
                candidate.getPart().name(),
                candidate.getTeam().name()
        );
    }
}