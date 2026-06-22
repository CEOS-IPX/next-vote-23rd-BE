package com.ceos.voting.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /* ========== Common (C) ========== */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C002", "인증이 필요합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C003", "서버 내부 오류가 발생했습니다."),

    /* ========== Auth (A) ========== */
    USER_NOT_FOUND(HttpStatus.UNAUTHORIZED, "A001", "해당 아이디를 가진 계정이 없습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "A002", "비밀번호가 일치하지 않습니다."),
    REFRESH_TOKEN_MISSING(HttpStatus.UNAUTHORIZED, "A003", "RefreshToken이 존재하지 않습니다."),
    REFRESH_TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "A004", "유효하지 않은 RefreshToken입니다."),
    REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "A005", "저장된 RefreshToken과 일치하지 않습니다."),

    /* ========== User / Signup (U) ========== */
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "U001", "비밀번호와 비밀번호 재확인이 일치하지 않습니다."),
    INVALID_CANDIDATE(HttpStatus.BAD_REQUEST, "U002", "유효하지 않은 후보입니다."),
    DUPLICATED_USERNAME(HttpStatus.CONFLICT, "U003", "이미 사용 중인 아이디입니다."),
    DUPLICATED_EMAIL(HttpStatus.CONFLICT, "U004", "이미 사용 중인 이메일입니다."),
    DUPLICATED_CANDIDATE(HttpStatus.CONFLICT, "U005", "해당 후보는 이미 가입되어 있습니다."),

    /* ========== Vote (V) ========== */
    INVALID_TEAM(HttpStatus.BAD_REQUEST, "V001", "유효하지 않은 팀입니다."),
    WRONG_PART(HttpStatus.BAD_REQUEST, "V002", "본인 파트의 후보만 선택할 수 있습니다."),
    SELF_VOTE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "V003", "본인에게는 투표할 수 없습니다."),
    OWN_TEAM_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "V004", "본인 팀에는 투표할 수 없습니다."),
    DUPLICATED_VOTE(HttpStatus.CONFLICT, "V005", "이미 투표하셨습니다."),
    VOTING_CLOSED(HttpStatus.GONE, "V006", "투표가 마감되었습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
