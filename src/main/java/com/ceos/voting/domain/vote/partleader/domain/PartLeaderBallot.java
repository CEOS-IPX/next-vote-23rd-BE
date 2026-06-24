package com.ceos.voting.domain.vote.partleader.domain;

import com.ceos.voting.domain.candidate.domain.Candidate;
import com.ceos.voting.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "part_leader_ballot")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartLeaderBallot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 익명 투표 -> voter 정보 없음, 받은 후보만 기록
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Candidate candidate;

    public PartLeaderBallot(Candidate candidate) {
        this.candidate = candidate;
    }
}
