package com.lovememoir.server.domain.avatar;

import com.lovememoir.server.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String avatarType;

    @Column(nullable = false, length = 10)
    private String growthStage;

    @Column(nullable = false, length = 10)
    private String behavior;

    @Column(nullable = false, length = 100)
    private String question;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Avatar(String avatarType, String growthStage, String behavior, String question) {
        this.avatarType = avatarType;
        this.growthStage = growthStage;
        this.behavior = behavior;
        this.question = question;
    }
}
