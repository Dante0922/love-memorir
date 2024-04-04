package com.lovememoir.server.api.service.diarypage;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.lovememoir.server.common.message.ValidationMessage.FUTURE_DIARY_DATE;
import static com.lovememoir.server.common.message.ValidationMessage.MAX_LENGTH_DIARY_PAGE_TITLE;

public abstract class DiaryPageValidator {

    private static final int TITLE_MAXIMUM_LENGTH = 10;

    public static String validateTitle(String title) {
        title = removeSpace(title);

        if (isTitleLengthGraterThan(title)) {
            throw new IllegalArgumentException(MAX_LENGTH_DIARY_PAGE_TITLE);
        }

        return title;
    }

    public static LocalDate validateDiaryDate(LocalDateTime currentDateTime, LocalDate diaryDate) {
        if (isFuture(currentDateTime, diaryDate)) {
            throw new IllegalArgumentException(FUTURE_DIARY_DATE);
        }

        return diaryDate;
    }

    private static String removeSpace(final String text) {
        return text.strip();
    }

    private static boolean isTitleLengthGraterThan(final String title) {
        return title.length() > TITLE_MAXIMUM_LENGTH;
    }

    private static boolean isFuture(final LocalDateTime currentDateTime, final LocalDate diaryDate) {
        return currentDateTime.isBefore(diaryDate.atStartOfDay());
    }
}
