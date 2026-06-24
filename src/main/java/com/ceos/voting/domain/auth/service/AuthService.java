package com.ceos.voting.domain.auth.service;

import com.ceos.voting.domain.auth.dto.request.LoginRequest;
import com.ceos.voting.domain.auth.dto.response.LoginResponse;
import com.ceos.voting.domain.user.domain.User;
import com.ceos.voting.domain.user.repository.UserRepository;
import com.ceos.voting.global.exception.BusinessException;
import com.ceos.voting.global.exception.ErrorCode;
import com.ceos.voting.global.security.jwt.JwtTokenProvider;
import com.ceos.voting.domain.auth.dto.request.SignupRequest;
import com.ceos.voting.domain.auth.dto.response.SignupResponse;
import com.ceos.voting.domain.candidate.domain.Candidate;
import com.ceos.voting.domain.user.domain.User;
import com.ceos.voting.domain.user.repository.UserRepository;
import com.ceos.voting.global.common.Part;
import com.ceos.voting.global.common.Team;
import com.ceos.voting.global.exception.BusinessException;
import com.ceos.voting.global.exception.ErrorCode;
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

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        validatePasswordConfirm(request.password(), request.passwordConfirm());
        validateDuplicatedUsername(request.username());
        validateDuplicatedEmail(request.email());

        Candidate candidate = getCandidate(request.name(), request.part(), request.team());
        validateDuplicatedCandidate(candidate);

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = User.create(request.username(), encodedPassword, request.email(), candidate);
        User savedUser = userRepository.save(user);

        return SignupResponse.from(savedUser);
    }

    private void validatePasswordConfirm(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }
    }

    private void validateDuplicatedUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new BusinessException(ErrorCode.DUPLICATED_USERNAME);
        }
    }

    private void validateDuplicatedEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    private void validateDuplicatedCandidate(Candidate candidate) {
        if (userRepository.existsByCandidate(candidate)) {
            throw new BusinessException(ErrorCode.DUPLICATED_CANDIDATE);
        }
    }

    private Candidate getCandidate(String name, String partValue, String teamValue) {
        try {
            Part part = Part.valueOf(partValue);
            Team team = Team.valueOf(teamValue);
            return Candidate.of(name, part, team);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_CANDIDATE);
        }
    }
}