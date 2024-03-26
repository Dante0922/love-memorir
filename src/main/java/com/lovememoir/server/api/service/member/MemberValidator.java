package com.lovememoir.server.api.service.member;

import static com.lovememoir.server.common.message.ValidationMessage.MAX_LENGTH_NICKNAME;

public abstract class MemberValidator {

    private static final int NICKNAME_MAXIMUM_LENGTH = 8;

    public static String validateNickname(String nickname) {
        nickname = removeSpace(nickname);

        if (isNicknameLengthGraterThan(nickname)) {
            throw new IllegalArgumentException(MAX_LENGTH_NICKNAME);
        }

        return nickname;
    }

    private static String removeSpace(final String text) {
        return text.strip();
    }
    private static boolean isNicknameLengthGraterThan(final String nickname) {
        return nickname.length() > NICKNAME_MAXIMUM_LENGTH;
    }

}
