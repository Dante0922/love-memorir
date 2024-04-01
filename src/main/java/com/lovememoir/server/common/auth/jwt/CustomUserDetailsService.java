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

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthQueryRepository authQueryRepository;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth = authQueryRepository.findByProviderId(username);
        Member member = memberQueryRepository.findByAuthId(auth.getId());
        log.info("loadUserByUsername member : {}", member);
        log.info("loadUserByUsername auth : {}", auth);

        if (auth == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
//        return new CustomUserDetails(member, auth);
        return new CustomUser(member, auth);
    }
}
