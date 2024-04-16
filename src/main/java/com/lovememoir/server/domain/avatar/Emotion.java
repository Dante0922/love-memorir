package com.lovememoir.server.domain.avatar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Emotion {
    HAPPINESS("행복"),
    ROMANCE("설렘"),
    STABILITY("안정"),
    SADNESS("슬픔"),
    ANGER("분노");

    private final String text;
}
