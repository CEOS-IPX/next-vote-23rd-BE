package com.ceos.voting.domain.user.repository;

import com.ceos.voting.domain.candidate.domain.Candidate;
import com.ceos.voting.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByCandidate(Candidate candidate);
}