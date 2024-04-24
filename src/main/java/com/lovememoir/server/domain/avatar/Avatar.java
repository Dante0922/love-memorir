package com.lovememoir.server.domain.avatar;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @Column
    private LocalDateTime questionModifiedDateTime;

    @Builder
    private Avatar(Emotion emotion, String question, Member member, LocalDateTime modifiedDateTime) {
        this.emotion = emotion;
        this.question = question;
        this.member = member;
        this.questionModifiedDateTime = modifiedDateTime != null ? modifiedDateTime : LocalDateTime.now();
    }

    public static Avatar create(Emotion emotion, String question, Member member) {
        return Avatar.builder()
            .emotion(emotion)
            .question(question)
            .member(member)
            .build();
    }

    public void modified(Emotion emotion, String question) {
        this.emotion = emotion;
        this.question = question;
        questionModifiedDateTime = LocalDateTime.now();
    }
}
