package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.domain.diary.LoveInfo;

import java.time.LocalDate;

import static com.lovememoir.server.common.message.ValidationMessage.*;

public abstract class DiaryValidator {

    private static final int TITLE_MAXIMUM_LENGTH = 8;

    private static final String TITLE_REGEX = "^[가-핳0-9]*$";

    public static String validateTitle(String title) {
        title = title.strip();

        if (title.length() > TITLE_MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(MAX_LENGTH_DIARY_TITLE);
        }

        if (!title.matches(TITLE_REGEX)) {
            throw new IllegalArgumentException(NOT_MATCHES_PATTERN_DIARY_TITLE);
        }

        return title;
    }

    public static LoveInfo validateLoveInfo(boolean isLove, final LocalDate startedDate, final LocalDate finishedDate, final LocalDate currentDate) {
        if (isLove && startedDate == null) {
            throw new IllegalArgumentException(NOT_NULL_STARTED_DATE);
        }

        if (startedDate != null && currentDate.isBefore(startedDate)) {
            throw new IllegalArgumentException(IS_FUTURE_DATE);
        }

        if (finishedDate != null && currentDate.isBefore(finishedDate)) {
            throw new IllegalArgumentException(IS_FUTURE_DATE);
        }

        if (startedDate != null && finishedDate != null && finishedDate.isBefore(startedDate)) {
            throw new IllegalArgumentException(IS_FUTURE_DATE);
        }

        return LoveInfo.create(isLove, startedDate, finishedDate);
    }
}
