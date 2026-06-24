package com.ceos.voting.domain.vote.demoday.domain;

import com.ceos.voting.global.common.Team;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "demo_day_ballot")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DemoDayBallot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Team team;

    public DemoDayBallot(Team team) {
        this.team = team;
    }
}
