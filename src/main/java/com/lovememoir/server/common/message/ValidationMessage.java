package com.lovememoir.server.common.message;

/**
 * Request Date Validation Message
 *
 * @author dong82
 */
public abstract class ValidationMessage {

    public static final String NOT_BLANK_DIARY_TITLE = "파트너 닉네임을 입력하세요.";
  
    public static final String NOT_BLANK_MEMBER_NICKNAME = "사용자 닉네임을 입력하세요.";
    public static final String NOT_BLANK_MEMBER_GENDER = "사용자 성별을 입력하세요.";
    public static final String NOT_BLANK_MEMBER_BIRTH = "사용자 생년월일을 입력하세요.";
    public static final String NOT_NULL_MEMBER_ID = "사용자 고객번호를 입력하세요.";

    public static final String NOT_BLANK_DIARY_PAGE_TITLE = "일기 제목을 입력하세요.";
    public static final String NOT_BLANK_DIARY_PAGE_CONTENT = "일기 내용을 입력하세요.";
    public static final String NOT_BLANK_DIARY_PAGE_DATE = "일자를 입력하세요.";
}
