package com.ceos.voting.domain.vote.partleader.domain;

import com.ceos.voting.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "part_leader_participation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartLeaderParticipation extends BaseEntity {

    // User.id와 동일 (한 유저당 1행 보장)
    @Id
    @Column(name = "user_id")
    private Long userId;

    public PartLeaderParticipation(Long userId) {
        this.userId = userId;
    }
}