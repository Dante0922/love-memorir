package com.lovememoir.server.domain.member;

import org.springframework.security.core.userdetails.User;

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
