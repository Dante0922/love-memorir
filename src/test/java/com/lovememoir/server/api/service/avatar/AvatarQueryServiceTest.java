package com.lovememoir.server.api.service.avatar;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.avatar.Avatar;
import com.lovememoir.server.domain.avatar.repository.AvatarRepository;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.lovememoir.server.domain.avatar.Emotion.HAPPINESS;
import static com.lovememoir.server.domain.avatar.Emotion.STABILITY;
import static org.assertj.core.api.Assertions.assertThat;

class AvatarQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private AvatarQueryService avatarQueryService;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private AvatarRepository avatarRepository;

    @DisplayName("질문생성시간이 만료되지 않은 아바타의 조회")
    @Test
    void searchAvatar() throws Exception {
        //given
        Member member = createMember();
        Auth auth = createAuth(member);
        Avatar avatar = createAvatarWithQuestionModifiedDate(member, LocalDateTime.now());

        //when
        AvatarResponse response = avatarQueryService.searchAvatar(auth.getProviderId());

        //then
        assertThat(response)
            .hasFieldOrPropertyWithValue("emotion", HAPPINESS)
            .hasFieldOrPropertyWithValue("question", "테스트");
    }

    @DisplayName("질문생성시간이 만료된 아바타의 조회")
    @Test
    void searchAvatarWithExpiredQuestion() throws Exception {
        //given
        Member member = createMember();
        Auth auth = createAuth(member);
        Avatar avatar = createAvatarWithQuestionModifiedDate(member, LocalDateTime.now().minusDays(2));

        //when
        AvatarResponse response = avatarQueryService.searchAvatar(auth.getProviderId());

        //then
        assertThat(response.getEmotion()).isEqualTo(STABILITY);
        assertThat(response.getQuestion()).isNotEqualTo("테스트");

    }

    private Member createMember() {
        Member member = Member.builder()
            .nickname("아이바오")
            .gender(Gender.F)
            .birth("2013-07-13")
            .roleType(RoleType.USER)
            .build();
        return memberRepository.save(member);
    }

    private Auth createAuth(Member member) {
        Auth auth = Auth.builder()
            .provider(ProviderType.KAKAO)
            .providerId("0123456789")
            .accessToken("access.token")
            .refreshToken("refresh.token")
            .expiredDateTime(null)
            .member(member)
            .build();
        return authRepository.save(auth);
    }

    private Avatar createAvatarWithQuestionModifiedDate(Member member, LocalDateTime localDateTime) {
        Avatar avatar = Avatar.builder()
            .emotion(HAPPINESS)
            .question("테스트")
            .modifiedDateTime(localDateTime)
            .member(member)
            .build();
        avatarRepository.save(avatar);
        return avatar;
    }
}