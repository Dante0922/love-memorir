package com.lovememoir.server.common.auth.jwt;

import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.member.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Member member;
    private final Auth auth;

    public CustomUserDetails(Member member, Auth auth) {
        this.member = member;
        this.auth = auth;
    }

    /* MEMBER의 권한이 1개만 있어서 add로 구현함*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> {
                return member.getRoleType().toString();
            }
        );
        return authorities;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return member.getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
