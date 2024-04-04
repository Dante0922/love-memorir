package com.lovememoir.server;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class WithAuthMemberSecurityContextFactory implements WithSecurityContextFactory<WithAuthMember> {

    @Override
    public SecurityContext createSecurityContext(WithAuthMember annotation) {
        String providerId = annotation.providerId();
        String role = annotation.role();
        User principal = new User(providerId, "", List.of(new SimpleGrantedAuthority(role)));

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "password", List.of(new SimpleGrantedAuthority(role)));
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
        return context;
    }
}
