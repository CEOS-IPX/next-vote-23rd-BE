package com.ceos.voting.domain.vote.demoday.domain;

import com.ceos.voting.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "demo_day_participation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DemoDayParticipation extends BaseEntity {

    @Id
    @Column(name = "user_id")
    private Long userId;

    public DemoDayParticipation(Long userId) {
        this.userId = userId;
    }
}
