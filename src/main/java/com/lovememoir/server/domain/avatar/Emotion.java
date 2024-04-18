package com.lovememoir.server.domain.avatar;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.lovememoir.server.common.message.ExceptionMessage.INVALID_EMOTION_CODE;

@Getter
@AllArgsConstructor
public enum Emotion {
    HAPPINESS(1, "행복"),
    ROMANCE(2, "설렘"),
    STABILITY(3, "안정"),
    SADNESS(4, "슬픔"),
    ANGER(5, "분노");

    private final int code;
    private final String text;

    public static Emotion fromCode(int code) {
        for (Emotion emotion : Emotion.values()) {
            if (emotion.code == code) {
                return emotion;
            }
        }
        throw new IllegalArgumentException(INVALID_EMOTION_CODE);
    }
}
