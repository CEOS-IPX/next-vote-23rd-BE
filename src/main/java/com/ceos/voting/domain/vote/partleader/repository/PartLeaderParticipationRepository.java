package com.ceos.voting.domain.vote.partleader.repository;

import com.ceos.voting.domain.vote.partleader.domain.PartLeaderParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartLeaderParticipationRepository extends JpaRepository<PartLeaderParticipation, Long> {

    boolean existsByUserId(Long userId);
}