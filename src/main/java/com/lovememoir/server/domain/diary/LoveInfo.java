package com.lovememoir.server.domain.diary;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class LoveInfo {

    @Column(nullable = false)
    private final boolean isLove;

    private final LocalDate startedDate;

    private final LocalDate finishedDate;

    @Builder
    private LoveInfo(boolean isLove, LocalDate startedDate, LocalDate finishedDate) {
        this.isLove = isLove;
        this.startedDate = startedDate;
        this.finishedDate = finishedDate;
    }
}
