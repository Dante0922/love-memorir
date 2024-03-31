package com.lovememoir.server.common.message;

import static com.lovememoir.server.common.constant.GlobalConstant.MAX_DIARY_COUNT;

public abstract class ExceptionMessage {

    public static final String NO_SUCH_MEMBER = "회원 정보를 찾을 수 없습니다.";
    public static final String NO_SUCH_DIARY = "일기장 정보를 찾을 수 없습니다.";
    public static final String NO_SUCH_DIARY_PAGE = "일기 정보를 찾을 수 없습니다.";
    public static final String NO_AUTH = "접근 권한이 없습니다.";
    public static final String MAXIMUM_DIARY_COUNT = String.format("일기장은 최대 %d개 생성 가능합니다.", MAX_DIARY_COUNT);

    public static final String USER_NOT_FOUND = "사용자를 찾을 수 없습니다.";
    public static final String FAILED_TO_GENERATE_TOKEN = "사용자 인증토큰 생성에 실패했습니다.";
    public static final String OAUTH_TOKEN_UNAUTHORIZED = "비정상 토큰입니다. 다시 로그인해주세요.";
    public static final String OAUTH_SERVER_ERROR = "소셜서비스로부터 응답이 없습니다.";

    public static final String FAIL_UPLOAD_FILE = "파일 업로드에 실패했습니다.";

}
