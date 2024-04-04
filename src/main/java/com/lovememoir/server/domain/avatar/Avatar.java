package com.lovememoir.server.domain.avatar;

import com.lovememoir.server.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Avatar extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id") // @TODO member_id PK 활용 예정.. @Inheritance? 이해하고 수정하자.
    private Long id;

    @Column(nullable = false, length = 10)
    private String avatarType;

    @Column(nullable = false, length = 10)
    private String growthStage;

    @Column(nullable = false, length = 10)
    private String behavior;

    @Column(nullable = false, length = 100)
    private String question;

    @Builder
    private Avatar(String avatarType, String growthStage, String behavior, String question) {
        this.avatarType = avatarType;
        this.growthStage = growthStage;
        this.behavior = behavior;
        this.question = question;
    }
}
