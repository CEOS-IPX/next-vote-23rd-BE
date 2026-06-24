package com.ceos.voting.domain.vote.demoday.repository;

import com.ceos.voting.domain.vote.partleader.domain.DemoDayParticipation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoDayParticipationRepository extends JpaRepository<DemoDayParticipation, Long> {

    boolean existsByUserId(Long userId);
}