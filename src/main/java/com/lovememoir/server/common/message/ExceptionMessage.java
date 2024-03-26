package com.lovememoir.server.common.message;

import static com.lovememoir.server.common.constant.GlobalConstant.MAX_DIARY_COUNT;

public abstract class ExceptionMessage {

    public static final String NO_SUCH_MEMBER = "회원 정보를 찾을 수 없습니다.";
    public static final String NO_SUCH_DIARY = "일기장 정보를 찾을 수 없습니다.";
    public static final String NO_SUCH_DIARY_PAGE = "일기 정보를 찾을 수 없습니다.";
    public static final String NO_AUTH = "접근 권한이 없습니다.";
    public static final String MAXIMUM_DIARY_COUNT = String.format("일기장은 최대 %d개 생성 가능합니다.", MAX_DIARY_COUNT);

}
