package com.lovememoir.server;

import com.lovememoir.server.common.auth.jwt.CustomUser;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class WithAuthMemberSecurityContextFactory implements WithSecurityContextFactory<WithAuthMember> {

    @Override
    public SecurityContext createSecurityContext(WithAuthMember annotation) {
        String providerId = annotation.providerId();
        String role = annotation.role();

        Auth auth = Auth.create(null,
            ProviderType.KAKAO,
            providerId,
            "asfasdfsadf",
            "asfasdfasdf",
            LocalDateTime.now());

        Member member = Member.create("MockMember",
            UUID.randomUUID().toString(),
            "hello@gmail.com",
            Gender.F,
            "2000-01-01",
            RoleType.valueOf(role),
            auth
        );

        UserDetails principal = new CustomUser(member, auth);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "password", List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
