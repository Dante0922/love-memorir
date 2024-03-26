package com.lovememoir.server.api.controller.diary.response;

import com.lovememoir.server.domain.diary.Diary;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryRemoveResponse {

    private final Long diaryId;
    private final String title;

    @Builder
    private DiaryRemoveResponse(Long diaryId, String title) {
        this.diaryId = diaryId;
        this.title = title;
    }

    public static DiaryRemoveResponse of(Diary diary) {
        return DiaryRemoveResponse.builder()
            .diaryId(diary.getId())
            .title(diary.getTitle())
            .build();
    }
}
