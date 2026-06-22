package com.ceos.voting.domain.candidate.domain;

import com.ceos.voting.global.common.Part;
import com.ceos.voting.global.common.Team;
import com.ceos.voting.global.exception.BusinessException;
import com.ceos.voting.global.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@RequiredArgsConstructor
public enum Candidate {

    // ===== IPX =====
    NAM_GIRIM    ("남기림", Part.FRONTEND, Team.IPX),
    KIM_MINSEO   ("김민서", Part.FRONTEND, Team.IPX),
    OH_JISONG    ("오지송", Part.BACKEND,  Team.IPX),
    KIM_TAEIK    ("김태익", Part.BACKEND,  Team.IPX),

    // ===== CONX =====
    KIM_HONGYEOP ("김홍엽", Part.FRONTEND, Team.CONX),
    OH_YUJIN     ("오유진", Part.FRONTEND, Team.CONX),
    KIM_TAEHEE   ("김태희", Part.BACKEND,  Team.CONX),
    KIM_DOHYEON  ("김도현", Part.BACKEND,  Team.CONX),

    // ===== GROUPEAT =====
    LEE_SEUNGYEON ("이승연", Part.FRONTEND, Team.GROUPEAT),
    HWANG_YEONGJUN("황영준", Part.FRONTEND, Team.GROUPEAT),
    KIM_DONGWOOK  ("김동욱", Part.BACKEND,  Team.GROUPEAT),
    CHOI_SEUNGWON ("최승원", Part.BACKEND,  Team.GROUPEAT),

    // ===== DITDA =====
    PARK_YUMIN   ("박유민", Part.FRONTEND, Team.DITDA),
    KWON_OJIN    ("권오진", Part.FRONTEND, Team.DITDA),
    IM_JONGHUN   ("임종훈", Part.BACKEND,  Team.DITDA),
    AN_JUNSEOK   ("안준석", Part.BACKEND,  Team.DITDA),

    // ===== JOBDRI =====
    LEE_YUNSEO   ("이윤서", Part.FRONTEND, Team.JOBDRI),
    GU_MINGYO    ("구민교", Part.FRONTEND, Team.JOBDRI),
    HWANG_SINAE  ("황신애", Part.BACKEND,  Team.JOBDRI),
    CHOI_WOOHYEOK("최우혁", Part.BACKEND,  Team.JOBDRI);

    private final String displayName;
    private final Part part;
    private final Team team;

    // (이름, 파트, 팀) 조합으로 후보 찾기 (회원가입 검증용)
    public static Candidate of(String displayName, Part part, Team team) {
        return Arrays.stream(values())
                .filter(c -> c.displayName.equals(displayName)
                        && c.part == part
                        && c.team == team)
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CANDIDATE));
    }

    // 이름으로 후보 찾기 (투표 시 후보 식별용)
    public static Candidate findByName(String displayName) {
        return Arrays.stream(values())
                .filter(c -> c.displayName.equals(displayName))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CANDIDATE));
    }

    // 특정 파트의 정렬된 후보 목록
    public static List<Candidate> findByPart(Part part) {
        return Arrays.stream(values())
                .filter(c -> c.part == part)
                .sorted((a, b) -> a.displayName.compareTo(b.displayName))
                .toList();
    }

    // 특정 파트 + 팀의 후보 목록 (회원가입 dropdown용)
    public static List<Candidate> findByPartAndTeam(Part part, Team team) {
        return Arrays.stream(values())
                .filter(c -> c.part == part && c.team == team)
                .toList();
    }
}
