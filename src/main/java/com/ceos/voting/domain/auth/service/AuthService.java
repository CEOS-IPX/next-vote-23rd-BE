package com.ceos.voting.domain.auth.service;

import com.ceos.voting.domain.auth.dto.request.LoginRequest;
import com.ceos.voting.domain.auth.dto.response.LoginResponse;
import com.ceos.voting.domain.user.domain.User;
import com.ceos.voting.domain.user.repository.UserRepository;
import com.ceos.voting.global.exception.BusinessException;
import com.ceos.voting.global.exception.ErrorCode;
import com.ceos.voting.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);

        return LoginResponse.of(
                accessToken,
                jwtTokenProvider.getAccessTokenExpirationSeconds(),
                user
        );
    }
}