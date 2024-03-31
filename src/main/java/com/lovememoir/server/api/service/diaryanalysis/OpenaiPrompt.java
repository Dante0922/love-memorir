package com.lovememoir.server.api.service.diaryanalysis;

public class OpenaiPrompt {

    private static final String COMMAND = """
        이 내용을 분석해줬으면 좋겠어. 우리가 알고 싶은 감정은 행복, 설렘, 안정, 슬픔, 분노 5가지야.
        감정 분석한 결과를 1(낮음)~10(높음)으로 수치화해서 "행복: 1" 이런 형식으로만 답변 해줘.
        """;

    public static String generatePrompt(String content) {
        return content + "\n" + COMMAND;
    }
}
