package com.lovememoir.server.api.service.diary;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class DiaryValidator {

    public static String validateTitle(String title) {
        return null;
    }

    public static LocalDate validateRelationshipStartedDate(LocalDateTime currentDateTime, LocalDate relationshipStartedDate) {
        return null;
    }
}
