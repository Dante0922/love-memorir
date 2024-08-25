package com.lovememoir.server.api.service.diaryanalysis;

public class OpenaiPrompt {

    private static final String COMMAND = """
        위 일기의 감정을  json 형태로만 분석해서 반환해줘.
        감정의 종류는 STABILITY, HAPPINESS, ROMANCE, SADNESS, ANGER 다섯가지이고 0~100의 점수화해줘.
        다른 말 하지말고 json만 보내.
        """;

    public static String generatePrompt(String content) {
        return content + "\n" + COMMAND;
    }
}
