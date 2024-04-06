package com.lovememoir.server.api.service.member;

import static com.lovememoir.server.common.message.ValidationMessage.INVALID_NICKNAME_PATTERN;
import static com.lovememoir.server.common.message.ValidationMessage.MAX_LENGTH_NICKNAME;

public abstract class MemberValidator {

    private static final int NICKNAME_MAXIMUM_LENGTH = 8;
    private static final String NICKNAME_PATTERN = "[가-힣0-9]+$";

    public static String validateNickname(String nickname) {

        if (isNicknameLengthGraterThan(nickname)) {
            throw new IllegalArgumentException(MAX_LENGTH_NICKNAME);
        }

        if (!isValidNicknamePattern(nickname)) {
            throw new IllegalArgumentException(INVALID_NICKNAME_PATTERN);
        }

        return nickname;
    }


    private static boolean isNicknameLengthGraterThan(final String nickname) {
        return nickname.length() > NICKNAME_MAXIMUM_LENGTH;
    }

    private static boolean isValidNicknamePattern(final String nickname) {
        return nickname.matches(NICKNAME_PATTERN);
    }

}
