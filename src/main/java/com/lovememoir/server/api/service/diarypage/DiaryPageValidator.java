package com.lovememoir.server.api.service.diarypage;

import java.time.LocalDate;

public abstract class DiaryPageValidator {

    private static final int TITLE_MAXIMUM_LENGTH = 10;

    public static String validateTitle(String title) {
        return null;
    }

    public static LocalDate validateRecordDate(LocalDate recordDate, LocalDate currentDate) {
        return null;
    }
}
