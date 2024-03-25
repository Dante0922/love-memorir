package com.lovememoir.server.api.service.diary;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.lovememoir.server.common.message.ValidationMessage.FUTURE_DATE;
import static com.lovememoir.server.common.message.ValidationMessage.MAX_LENGTH_TITLE;

public abstract class DiaryValidator {

    private static final int TITLE_MAXIMUM_LENGTH = 8;

    public static String validateTitle(String title) {
        title = title.strip();

        if (title.length() > TITLE_MAXIMUM_LENGTH) {
            throw new IllegalArgumentException(MAX_LENGTH_TITLE);
        }

        return title;
    }

    public static LocalDate validateRelationshipStartedDate(LocalDateTime currentDateTime, LocalDate relationshipStartedDate) {
        if (currentDateTime.isBefore(relationshipStartedDate.atStartOfDay())) {
            throw new IllegalArgumentException(FUTURE_DATE);
        }

        return relationshipStartedDate;
    }
}
