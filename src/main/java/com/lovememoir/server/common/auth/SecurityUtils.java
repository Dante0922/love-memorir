package com.lovememoir.server.common.auth;

import com.lovememoir.server.common.auth.jwt.CustomUser;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.member.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Security Context에 인증 정보가 없습니다.");
        }
        CustomUser principal = (CustomUser) authentication.getPrincipal();
        return  principal.getMember();
    }
    public static Auth getCurrentAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Security Context에 인증 정보가 없습니다.");
        }
        CustomUser principal = (CustomUser) authentication.getPrincipal();
        return  principal.getAuth();
    }
}
