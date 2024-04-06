package com.lovememoir.server.api.service.diarypage;

import java.time.LocalDate;

import static com.lovememoir.server.common.message.ValidationMessage.IS_FUTURE_DATE;
import static com.lovememoir.server.common.message.ValidationMessage.MAX_LENGTH_DIARY_PAGE_TITLE;

public abstract class DiaryPageValidator {

    private static final int TITLE_MAXIMUM_LENGTH = 20;

    public static String validateTitle(String title) {
        title = title.strip();

        if (title.length() > TITLE_MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(MAX_LENGTH_DIARY_PAGE_TITLE);
        }

        return title;
    }

    public static LocalDate validateRecordDate(LocalDate recordDate, LocalDate currentDate) {
        if (currentDate.isBefore(recordDate)) {
            throw new IllegalArgumentException(IS_FUTURE_DATE);
        }
        return recordDate;
    }
}
