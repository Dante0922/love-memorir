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
    @Column(name = "avatar_id")
    private Long id;

    @Column(nullable = false, length = 10)
    private String emotion;

    @Column(nullable = false, length = 100)
    private String question;

    @Builder
    private Avatar(String emotion, String question) {
        this.emotion = emotion;
        this.question = question;
    }
}
