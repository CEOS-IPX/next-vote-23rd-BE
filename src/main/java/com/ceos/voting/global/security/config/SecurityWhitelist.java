package com.ceos.voting.global.security.config;

// 화이트리스트 상수 클래스
public final class SecurityWhitelist {

    private SecurityWhitelist() {
        // 인스턴스 생성 방지
    }

    // ===== 누구나 접근 가능한 경로 =====
    public static final String[] PERMIT_ALL_PATHS = {
            "/health",

            // 인증 관련 API
            "/api/auth/signup",                       // 회원가입
            "/api/auth/login",                        // 로그인
            "/api/auth/refresh",                      // AccessToken 재발급 (RT 쿠키로 인증)

            // 후보/멤버 조회 (비로그인도 페이지 접근 가능)
            "/api/candidates",                        // 회원가입 dropdown용 후보 조회
            "/api/votes/part-leader/candidates",      // 파트장 후보 조회
            "/api/votes/demo-day/candidates",         // 데모데이 후보 조회
            "/api/members",                           // 23기 멤버 목록

            // 투표 결과 조회 (실시간 공개)
            "/api/votes/part-leader/result",          // 파트장 투표 결과
            "/api/votes/demo-day/result",             // 데모데이 투표 결과

            // 헬스체크 / 모니터링
            "/actuator/health",
            "/actuator/info",

            // API 문서 (Swagger 사용 시)
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
    };

    // ===== 인증 필요한 경로 =====
    public static final String[] AUTHENTICATED_PATHS = {
            "/api/auth/logout",                       // 로그아웃

            // 투표 (로그인 사용자만)
            "/api/votes/part-leader",                 // 파트장 투표
            "/api/votes/demo-day",                    // 데모데이 투표
    };
}