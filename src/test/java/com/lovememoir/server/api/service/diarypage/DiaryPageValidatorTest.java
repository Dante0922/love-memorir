package com.lovememoir.server.api.service.diarypage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.lovememoir.server.common.message.ValidationMessage.FUTURE_DIARY_DATE;
import static com.lovememoir.server.common.message.ValidationMessage.MAX_LENGTH_DIARY_PAGE_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiaryPageValidatorTest {

    @DisplayName("입력 받은 제목의 길이가 10자를 초과하면 예외가 발생한다.")
    @Test
    void validateTitleWithOutOfLength() {
        //given
        String title = "푸바오 닮은 루이바오";

        //when //then
        assertThatThrownBy(() -> DiaryPageValidator.validateTitle(title))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(MAX_LENGTH_DIARY_PAGE_TITLE);
    }

    @DisplayName("입력 받은 제목의 앞 뒤 공백은 글자수에 포함되지 않는다.")
    @Test
    void validateTitleWithSpace() {
        //given
        String title = " 말썽쟁이 후이바오!    ";

        //when
        String validatedTitle = DiaryPageValidator.validateTitle(title);

        //then
        assertThat(validatedTitle).isEqualTo("말썽쟁이 후이바오!");
    }

    @DisplayName("입력 받은 제목에 대해 유효성 검증된 제목을 반환한다.")
    @Test
    void validateTitle() {
        //given
        String title = "말썽쟁이 후이바오!";

        //when
        String validatedTitle = DiaryPageValidator.validateTitle(title);

        //then
        assertThat(validatedTitle).isEqualTo("말썽쟁이 후이바오!");
    }

    @DisplayName("입력 받은 연애 시작일이 미래라면 예외가 발생한다.")
    @Test
    void validateDiaryDateWithFuture() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 10, 23, 59);
        LocalDate diaryDate = LocalDate.of(2024, 3, 11);

        //when //then
        assertThatThrownBy(() -> DiaryPageValidator.validateDiaryDate(currentDateTime, diaryDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(FUTURE_DIARY_DATE);
    }

    @DisplayName("입력 받은 연애 시작일에 대해 유효성 검증된 연애 시작일을 반환한다.")
    @Test
    void validateDiaryDate() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 10, 0, 0);
        LocalDate diaryDate = LocalDate.of(2024, 3, 10);

        //when
        LocalDate validatedDiaryDate = DiaryPageValidator.validateDiaryDate(currentDateTime, diaryDate);

        //then
        assertThat(validatedDiaryDate).isEqualTo(LocalDate.of(2024, 3, 10));
    }
}