package com.lovememoir.server.common.auth.jwt;

import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.repository.AuthQueryRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.lovememoir.server.common.message.ExceptionMessage.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthQueryRepository authQueryRepository;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    public UserDetails loadUserByUsername(String providerId) throws UsernameNotFoundException {
        Auth auth = authQueryRepository.findByProviderId(providerId);
        Member member = memberQueryRepository.findByAuthId(auth.getId());
        log.info("loadUserByUsername member : {}", member);
        log.info("loadUserByUsername auth : {}", auth);

        if (auth == null) {
            throw new UsernameNotFoundException(USER_NOT_FOUND);
        }
        return new CustomUser(member, auth);
    }
}
