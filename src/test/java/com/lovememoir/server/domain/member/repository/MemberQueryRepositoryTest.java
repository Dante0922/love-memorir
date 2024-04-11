package com.lovememoir.server.domain.member.repository;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

public class MemberQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthRepository authRepository;

    @DisplayName("ProviderId로 회원을 조회한다.")
    @Test
    void findByMemberWithProviderId() {
        //given
        String providerId = "0123456789";
        Auth auth = createAuth(providerId);
        Member member = createMember(auth, false);
        //when
        Member foundMember = memberQueryRepository.findByProviderId(providerId);

        //then
        assertThat(foundMember.getId()).isEqualTo(member.getId());
        assertThat(foundMember.getNickname()).isEqualTo(member.getNickname());
        assertThat(foundMember.getGender()).isEqualTo(member.getGender());
        assertThat(foundMember.getBirth()).isEqualTo(member.getBirth());
        assertThat(foundMember.getRoleType()).isEqualTo(member.getRoleType());
    }

    @DisplayName("ProviderId로 삭제된 회원을 조회한다.")
    @Test
    void findByRemovedMemberWithProviderId() {
        //given
        String providerId = "0123456789";
        Auth auth = createAuth(providerId);
        Member member = createMember(auth, true);
        //when
        Member foundMember = memberQueryRepository.findByProviderId(providerId);

        //then
        assertThat(foundMember).isNull();
    }

    private Auth createAuth(String providerId) {
        Auth auth = Auth.builder()
            .provider(ProviderType.KAKAO)
            .providerId(providerId)
            .build();
        return authRepository.save(auth);
    }

    private Member createMember(Auth auth, Boolean isDeleted) {
        Member member = Member.builder()
            .nickname("루이바")
            .gender(Gender.F)
            .birth("2016-03-03")
            .roleType(RoleType.USER)
            .auth(auth)
            .build();
        if (isDeleted) member.remove();
        return memberRepository.save(member);
    }
}
