package com.lovememoir.server.domain.diarypage.repository.response;

import com.lovememoir.server.domain.avatar.Emotion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DiaryAnalysisRseponse {
    private Integer emotionCode;
    private Integer weight;
    private String emotionName;

    public void setEmotionName(){
        this.emotionName = Emotion.fromCode(emotionCode).toString();
    }
}
