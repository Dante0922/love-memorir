package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.domain.diary.LoveInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.lovememoir.server.common.message.ValidationMessage.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DiaryValidatorTest {

    @DisplayName("입력 받은 제목의 길이가 8자리를 초과하면 예외가 발생한다.")
    @Test
    void validateTitleWithOutOfLength() {
        //given
        String title = "루이바오와후이바오";

        //when //then
        assertThatThrownBy(() -> DiaryValidator.validateTitle(title))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(MAX_LENGTH_DIARY_TITLE);
    }

    @DisplayName("입력 받은 제목에 한글, 숫자 이외의 문자가 포함되어 있으면 예외가 발생한다.")
    @Test
    void validateTitleWithNotMatchesPattern() {
        //given
        String title = "Pubao";

        //when //then
        assertThatThrownBy(() -> DiaryValidator.validateTitle(title))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(NOT_MATCHES_PATTERN_DIARY_TITLE);
    }

    @DisplayName("입력 받은 제목의 데이터 유효성 검증을 한다.")
    @Test
    void validateTitle() {
        //given
        String title = "푸바오";

        //when
        String validatedTitle = DiaryValidator.validateTitle(title);

        //then
        assertThat(validatedTitle).isEqualTo(title);
    }

    @DisplayName("연애중일때 입력 받은 연애 시작일이 null이면 예외가 발생한다.")
    @Test
    void validateLoveInfoIsLoveTrueWithoutStartedDate() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 4, 6);

        //when //then
        assertThatThrownBy(() -> DiaryValidator.validateLoveInfo(true, null, null, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(NOT_NULL_STARTED_DATE);
    }

    @DisplayName("입력 받은 연애 시작일이 현재 날짜보다 미래라면 예외가 발생한다.")
    @Test
    void validateLoveInfoStartedDateIsFuture() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 4, 6);
        LocalDate startedDate = LocalDate.of(2024, 4, 7);

        //when //then
        assertThatThrownBy(() -> DiaryValidator.validateLoveInfo(true, startedDate, null, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(IS_FUTURE_DATE);
    }

    @DisplayName("입력 받은 연애 종료일이 현재 날짜보다 미래라면 예외가 발생한다.")
    @Test
    void validateLoveInfoFinishedDateIsFuture() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 4, 6);
        LocalDate startedDate = LocalDate.of(2024, 4, 1);
        LocalDate finishedDate = LocalDate.of(2024, 4, 7);


        //when //then
        assertThatThrownBy(() -> DiaryValidator.validateLoveInfo(false, startedDate, finishedDate, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(IS_FUTURE_DATE);
    }

    @DisplayName("입력 받은 연애 시작일이 연애 종료일보다 미래라면 예외가 발생한다.")
    @Test
    void validateLoveInfoDateIsFuture() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 4, 6);
        LocalDate startedDate = LocalDate.of(2024, 4, 5);
        LocalDate finishedDate = LocalDate.of(2024, 4, 4);


        //when //then
        assertThatThrownBy(() -> DiaryValidator.validateLoveInfo(false, startedDate, finishedDate, currentDate))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(IS_FUTURE_DATE);
    }

    @DisplayName("입력 받은 연애 정보의 데이터 유효성 검증을 한다")
    @Test
    void validateLoveInfo() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 4, 6);
        LocalDate startedDate = LocalDate.of(2024, 4, 6);
        LocalDate finishedDate = LocalDate.of(2024, 4, 6);

        //when
        LoveInfo loveInfo = DiaryValidator.validateLoveInfo(false, startedDate, finishedDate, currentDate);

        //then
        assertThat(loveInfo)
            .isNotNull()
            .hasFieldOrPropertyWithValue("isLove", false)
            .hasFieldOrPropertyWithValue("startedDate", LocalDate.of(2024, 4, 6))
            .hasFieldOrPropertyWithValue("finishedDate", LocalDate.of(2024, 4, 6));
    }
}