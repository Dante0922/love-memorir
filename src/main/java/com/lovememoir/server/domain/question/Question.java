package com.lovememoir.server.domain.question;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.avatar.Emotion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private Emotion emotion;

    @Column
    private String content;

    @Builder
    private Question(Long id, Emotion emotion, String content) {
        this.id = id;
        this.emotion = emotion;
        this.content = content;
    }
}
