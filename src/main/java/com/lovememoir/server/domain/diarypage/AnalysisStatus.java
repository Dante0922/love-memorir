package com.lovememoir.server.domain.diarypage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AnalysisStatus {

    BEFORE("분석전"),
    SUCCESS("분석성공"),
    FAIL("분석실패");

    private final String text;
}
