package com.lovememoir.server.domain.member;

import com.lovememoir.server.domain.member.enumerate.RoleType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MemberAdapter extends User {

    private Member member;

    public MemberAdapter(Member member) {
        super(member.getId().toString(), "", null);
        this.member = member;
    }}

//    @Override
//    public Collection<GrantedAuthority> getAuthorities(RoleType roleType) {
//        return super.getAuthorities();
//    }
//}
