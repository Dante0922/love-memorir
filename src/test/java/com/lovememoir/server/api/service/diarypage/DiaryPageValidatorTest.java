package com.lovememoir.server.api.service.diarypage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.lovememoir.server.common.message.ValidationMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiaryPageValidatorTest {

    @DisplayName("입력 받은 제목의 길이가 20자리를 초과하면 예외가 발생한다.")
    @Test
    void validateTitleWithOutOfLength() {
        //given
        String title = "푸바오, 루이바오, 후이바오 딸 세마리";

        //when //then
        assertThatThrownBy(() -> DiaryPageValidator.validateTitle(title))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(MAX_LENGTH_DIARY_PAGE_TITLE);
    }

    @DisplayName("입력 받은 제목의 데이터 유효성 검증을 한다.")
    @Test
    void validateTitle() {
        //given
        String title = "중국으로 여행을 떠난 우리 푸바오ㅜㅜ";

        //when
        String validatedTitle = DiaryPageValidator.validateTitle(title);

        //then
        assertThat(validatedTitle).isEqualTo(title);
    }

    @DisplayName("입력 받은 기록일이 현재 날짜보다 미래라면 예외가 발생한다.")
    @Test
    void validateRecordDateIsFuture() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 4, 6);
        LocalDate recordDate = LocalDate.of(2024, 4, 7);


        //when //then
        assertThatThrownBy(() -> DiaryPageValidator.validateRecordDate(recordDate, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(IS_FUTURE_DATE);
    }

    @DisplayName("입력 받은 기록일의 데이터 유효성 검증을 한다.")
    @Test
    void validateRecordDate() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 4, 6);
        LocalDate recordDate = LocalDate.of(2024, 4, 6);

        //when
        LocalDate validatedRecordDate = DiaryPageValidator.validateRecordDate(recordDate, currentDate);

        //then
        assertThat(validatedRecordDate).isEqualTo(LocalDate.of(2024, 4, 6));
    }
}