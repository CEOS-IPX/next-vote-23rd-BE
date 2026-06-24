package com.ceos.voting.domain.vote.partleader.repository;

import com.ceos.voting.domain.candidate.domain.Candidate;
import com.ceos.voting.domain.vote.partleader.domain.PartLeaderBallot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface PartLeaderBallotRepository extends JpaRepository<PartLeaderBallot, Long> {

    List<PartLeaderBallot> findAllByCandidateIn(List<Candidate> candidates);

    long countByCandidateIn(Collection<Candidate> candidates);
}