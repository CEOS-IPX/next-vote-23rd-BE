package com.ceos.voting.global.cookie;

import org.springframework.http.ResponseCookie;

public class CookieUtil {
    public static ResponseCookie createCookie(String key, String value, Long maxAgeSeconds) {
        return ResponseCookie.from(key, value)
                .httpOnly(true)
                .secure(true)
                .path("/api/auth")
                .maxAge(maxAgeSeconds)
                .sameSite("None")
                .build();
    }

    public static ResponseCookie emptyCookie(String key) {
        return ResponseCookie.from(key, "")
                .httpOnly(true)
                .secure(true)
                .path("/api/auth")
                .maxAge(0)
                .sameSite("None")
                .build();
    }
}