package com.lovememoir.server.domain.avatar;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.member.Member;
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
    @Column(name = "avatar_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 10)
    private Emotion emotion;

    @Column(nullable = false, length = 100)
    private String question;

    @Builder
    private Avatar(Emotion emotion, String question, Member member) {
        this.emotion = emotion;
        this.question = question;
        this.member = member;
    }

    public static Avatar create(Emotion emotion, String question, Member member) {
        return Avatar.builder()
            .emotion(emotion)
            .question(question)
            .member(member)
            .build();
    }
}
