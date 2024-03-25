package com.lovememoir.server.common.message;

import static com.lovememoir.server.common.constant.GlobalConstant.MAX_DIARY_COUNT;

public abstract class ExceptionMessage {

    public static final String MAXIMUM_DIARY_COUNT = String.format("일기장은 최대 %d개 생성 가능합니다.", MAX_DIARY_COUNT);
}
