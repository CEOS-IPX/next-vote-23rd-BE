package com.ceos.voting.domain.vote.demoday.repository;

import com.ceos.voting.domain.vote.partleader.domain.DemoDayBallot;
import com.ceos.voting.global.common.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DemoDayBallotRepository extends JpaRepository<DemoDayBallot, Long> {

    List<DemoDayBallot> findAllByTeamIn(Collection<Team> teams);
}