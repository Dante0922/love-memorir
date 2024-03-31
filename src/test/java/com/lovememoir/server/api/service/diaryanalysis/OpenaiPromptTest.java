package com.lovememoir.server.api.service.diaryanalysis;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenaiPromptTest {

    @DisplayName("내용을 입력 받아 Openai prompt를 생성한다.")
    @Test
    void generatePrompt() {
        //given
        String content = """
            현재 참 시간이 빠르구나 생각이 든다.
            이곳에서가 내 전부라는 생각이 전혀 들지 않아서 상처받지는 않는다.
            그냥 기분이 잠시 안 좋을 뿐이다.
            그리고 내가 누군가로부터 표를 받지 않는 것이 내가 매력이 없음을 의미하지 않는다.
            그래도 이 묘한 감정은 나를 고민에 빠트리게 한다.
            기분이 지금 좋지는 않지만 안 좋은 것은 아니다.
            """;

        //when
        String prompt = OpenaiPrompt.generatePrompt(content);

        //then
        String expected = """
            현재 참 시간이 빠르구나 생각이 든다.
            이곳에서가 내 전부라는 생각이 전혀 들지 않아서 상처받지는 않는다.
            그냥 기분이 잠시 안 좋을 뿐이다.
            그리고 내가 누군가로부터 표를 받지 않는 것이 내가 매력이 없음을 의미하지 않는다.
            그래도 이 묘한 감정은 나를 고민에 빠트리게 한다.
            기분이 지금 좋지는 않지만 안 좋은 것은 아니다.
                            
            이 내용을 분석해줬으면 좋겠어. 우리가 알고 싶은 감정은 행복, 설렘, 안정, 슬픔, 분노 5가지야.
            감정 분석한 결과를 1(낮음)~10(높음)으로 수치화해서 "행복: 1" 이런 형식으로만 답변 해줘.
            """;
        assertThat(prompt).isEqualTo(expected);
    }
}