package com.lovememoir.server.common.auth.jwt;

import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.member.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class CustomUser extends User {

    private final Member member;
    private final Auth auth;

    public CustomUser(Member member, Auth auth) {
        super(auth.getProviderId(), "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.member = member;
        this.auth = auth;
    }
}
