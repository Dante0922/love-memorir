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
    private String analysisDescription;

    @Builder
    private DiaryAnalysisRseponse(Integer emotionCode, Integer weight, String emotionName) {
        this.emotionCode = emotionCode;
        this.weight = weight;
        this.emotionName = emotionName;
        this.analysisDescription = generateAnalysisDescription(emotionCode, weight);

    }

    private String generateAnalysisDescription(Integer emotionCode, Integer weight) {
        String description;
        Emotion emotion = Emotion.fromCode(emotionCode);
        switch (emotion) {
            case HAPPINESS:
                description = "이날의 행복지수는 " + weight + "%네요.";
                break;
            case SADNESS:
                description = "이날의 슬픔지수는 " + weight + "%네요.";
                break;
            case ANGER:
                description = "이날의 분노지수는 " + weight + "%네요.";
                break;
            case ROMANCE:
                description = "이날의 설렘지수는 " + weight + "%네요.";
                break;
            case STABILITY:
                description = "이날의 안정지수는 " + weight + "%네요.";
                break;
            default:
                description = "이날의 감정지수는 " + weight + "%네요.";
                break;
        }
        return description;
    }

    public void setEmotionName(){
        this.emotionName = Emotion.fromCode(emotionCode).toString();
    }
}
