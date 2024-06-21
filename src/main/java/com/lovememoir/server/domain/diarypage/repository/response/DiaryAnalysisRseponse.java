package com.lovememoir.server.domain.diarypage.repository.response;

import com.lovememoir.server.domain.avatar.Emotion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class DiaryAnalysisRseponse {
    private Integer emotionCode;
    private Integer weight;
    private String emotionName;

    @Builder
    private DiaryAnalysisRseponse(Integer emotionCode, Integer weight, String emotionName) {
        this.emotionCode = emotionCode;
        this.weight = weight;
        this.emotionName = emotionName;
    }

    public void setEmotionName(){
        this.emotionName = Emotion.fromCode(emotionCode).toString();
    }
}
