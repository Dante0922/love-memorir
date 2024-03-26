package com.lovememoir.server.api.service.diarypage;

import java.time.LocalDate;
import java.time.LocalDateTime;

public abstract class DiaryPageValidator {

    public static String validateTitle(String title) {
        return null;
    }

    public static LocalDate validateDiaryDate(LocalDateTime currentDateTime, LocalDate diaryDate) {
        return null;
    }
}
