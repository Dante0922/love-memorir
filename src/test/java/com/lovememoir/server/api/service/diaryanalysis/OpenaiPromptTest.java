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
                            
            위 일기의 감정을  json 형태로만 분석해서 반환해줘.
            감정의 종류는 행복, 슬픔, 설렘, 안정, 분노 다섯가지이고 0~100의 점수화해줘.
            다른 말 하지말고 json만 보내.
            """;
        assertThat(prompt).isEqualTo(expected);
    }
}