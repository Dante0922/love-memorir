package com.lovememoir.server.api.controller.diarypage.response;

import com.lovememoir.server.domain.diarypage.AnalysisStatus;
import lombok.Builder;

public class DiaryPageEmotionResponse {


    private final long diaryPageId;
    private final String emotion;
    private final Integer emotionScroe;

    @Builder
    private DiaryPageEmotionResponse(long diaryPageId, String emotion, Integer emotionScroe) {
        this.diaryPageId = diaryPageId;
        this.emotion = emotion;
        this.emotionScroe = emotionScroe;
    }
}
