package com.lovememoir.server.api.service.diary;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.lovememoir.server.common.message.ValidationMessage.FUTURE_DATE;
import static com.lovememoir.server.common.message.ValidationMessage.MAX_LENGTH_TITLE;

public abstract class DiaryValidator {

    private static final int TITLE_MAXIMUM_LENGTH = 8;

    public static String validateTitle(String title) {
        title = removeSpace(title);

        if (isTitleLengthGraterThan(title)) {
            throw new IllegalArgumentException(MAX_LENGTH_TITLE);
        }

        return title;
    }

    public static LocalDate validateRelationshipStartedDate(final LocalDateTime currentDateTime, final LocalDate relationshipStartedDate) {
        if (isFuture(currentDateTime, relationshipStartedDate)) {
            throw new IllegalArgumentException(FUTURE_DATE);
        }

        return relationshipStartedDate;
    }

    private static String removeSpace(final String text) {
        return text.strip();
    }

    private static boolean isTitleLengthGraterThan(final String title) {
        return title.length() > TITLE_MAXIMUM_LENGTH;
    }

    private static boolean isFuture(final LocalDateTime currentDateTime, final LocalDate relationshipStartedDate) {
        return currentDateTime.isBefore(relationshipStartedDate.atStartOfDay());
    }
}
