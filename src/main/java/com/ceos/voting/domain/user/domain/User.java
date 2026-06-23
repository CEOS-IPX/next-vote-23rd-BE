package com.ceos.voting.domain.user.domain;

import com.ceos.voting.domain.candidate.domain.Candidate;
import com.ceos.voting.global.common.Part;
import com.ceos.voting.global.common.Team;
import com.ceos.voting.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 50)
    private Candidate candidate;

    private User(String username, String password, String email, Candidate candidate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.candidate = candidate;
    }

    // 회원 생성: password는 반드시 BCrypt 인코딩된 상태로 전달
    public static User create(String username, String encodedPassword, String email, Candidate candidate) {
        return new User(username, encodedPassword, email, candidate);
    }

    /* ===== Candidate 위임 ===== */

    public String getDisplayName() {
        return candidate.getDisplayName();
    }

    public Part getPart() {
        return candidate.getPart();
    }

    public Team getTeam() {
        return candidate.getTeam();
    }
}
