package com.lovememoir.server.api.controller.diary.response;

import com.lovememoir.server.domain.diary.Diary;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DiaryModifyResponse {

    private final Long diaryId;
    private final String title;

    @Builder
    private DiaryModifyResponse(Long diaryId, String title) {
        this.diaryId = diaryId;
        this.title = title;
    }

    public static DiaryModifyResponse of(Diary diary) {
        return DiaryModifyResponse.builder()
            .diaryId(diary.getId())
            .title(diary.getTitle())
            .build();
    }
}
