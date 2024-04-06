package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.domain.diary.LoveInfo;

import java.time.LocalDate;

public abstract class DiaryValidator {

    private static final int TITLE_MAXIMUM_LENGTH = 8;

    public static String validateTitle(String title) {
        return null;
    }

    public static LoveInfo validateLoveInfo(boolean isLove, final LocalDate startedDate, final LocalDate finishedDate, final LocalDate currentDate) {
        return null;
    }
}
