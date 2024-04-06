package com.lovememoir.server;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@WithSecurityContext(factory = WithAuthUserSecurityContextFactory.class)
public @interface WithAuthUser {
    String providerId() default "1234567890";
    String roles() default "USER";
}
