package com.lovememoir.server.common.message;

/**
 * Request Date Validation Message
 *
 * @author dong82
 */
public abstract class ValidationMessage {

    public static final String NOT_NULL_MEMBER_ID = "사용자 고객번호를 입력하세요.";
    public static final String NOT_BLANK_MEMBER_NICKNAME = "사용자 닉네임을 입력하세요.";
    public static final String NOT_NULL_MEMBER_GENDER = "사용자 성별을 입력하세요.";
    public static final String NOT_BLANK_MEMBER_BIRTH = "사용자 생년월일을 입력하세요.";
    public static final String MAX_LENGTH_NICKNAME = "사용자 닉네임은 최대 8자입니다.";

    public static final String NOT_NULL_AVATAR_TYPE = "아바타 타입을 입력하세요.";
    public static final String NOT_NULL_AVATAR_GROWTH_STAGE = "아바타 성장등급을 입력하세요.";

    public static final String NOT_BLANK_DIARY_TITLE = "연인의 이름 또는 애칭을 입력해주세요.";
    public static final String NOT_NULL_IS_LOVE = "연애 여부를 입력하세요.";
    public static final String MAX_LENGTH_DIARY_TITLE = "길이는 최대 8자입니다.";
    public static final String NOT_MATCHES_PATTERN_DIARY_TITLE = "한글과 숫자만 입력하세요.";
    public static final String NOT_NULL_STARTED_DATE = "연애 시작일을 입력하세요.";
    public static final String IS_FUTURE_DATE = "날짜를 올바르게 입력해주세요.";

    public static final String NOT_BLANK_DIARY_PAGE_TITLE = "일기 제목을 입력하세요.";
    public static final String NOT_BLANK_DIARY_PAGE_CONTENT = "일기 내용을 입력하세요.";
    public static final String NOT_NULL_DIARY_PAGE_DATE = "일자를 입력하세요.";
    public static final String MAX_LENGTH_DIARY_PAGE_TITLE = "일기 제목의 길이는 최대 10자입니다.";
    public static final String FUTURE_DIARY_DATE = "일기 날짜를 올바르게 입력해주세요.";
}
