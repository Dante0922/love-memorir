package com.lovememoir.server.api.service.diary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.lovememoir.server.common.message.ValidationMessage.FUTURE_DATE;
import static com.lovememoir.server.common.message.ValidationMessage.MAX_LENGTH_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiaryValidatorTest {

    @DisplayName("입력 받은 제목의 길이가 8자를 초과하면 예외가 발생한다.")
    @Test
    void validateTitleWithOutOfLength() {
        //given
        String title = "말썽쟁이 후이바오";

        //when //then
        assertThatThrownBy(() -> DiaryValidator.validateTitle(title))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(MAX_LENGTH_TITLE);
    }

    @DisplayName("입력 받은 제목의 앞 뒤 공백은 글자수에 포함되지 않는다.")
    @Test
    void validateTitleWithSpace() {
        //given
        String title = " 개구쟁이 푸바오 ";

        //when
        String validatedTitle = DiaryValidator.validateTitle(title);

        //then
        assertThat(validatedTitle).isEqualTo("개구쟁이 푸바오");
    }

    @DisplayName("입력 받은 제목에 대해 유효성 검증된 제목을 반환한다.")
    @Test
    void validateTitle() {
        //given
        String title = "개구쟁이 푸바오";

        //when
        String validatedTitle = DiaryValidator.validateTitle(title);

        //then
        assertThat(validatedTitle).isEqualTo("개구쟁이 푸바오");
    }

    @DisplayName("입력 받은 연애 시작일이 미래라면 예외가 발생한다.")
    @Test
    void validateRelationshipStartedDateWithFuture() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 10, 23, 59);
        LocalDate relationshipStartedDate = LocalDate.of(2024, 3, 11);

        //when //then
        assertThatThrownBy(() -> DiaryValidator.validateRelationshipStartedDate(currentDateTime, relationshipStartedDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(FUTURE_DATE);
    }

    @DisplayName("입력 받은 연애 시작일에 대해 유효성 검증된 연애 시작일을 반환한다.")
    @Test
    void validateRelationshipStartedDate() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 10, 0, 0);
        LocalDate relationshipStartedDate = LocalDate.of(2024, 3, 10);

        //when
        LocalDate validatedRelationshipStartedDate = DiaryValidator.validateRelationshipStartedDate(currentDateTime, relationshipStartedDate);

        //then
        assertThat(validatedRelationshipStartedDate).isEqualTo(LocalDate.of(2024, 3, 10));
    }
}