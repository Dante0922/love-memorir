package com.lovememoir.server.domain.avatar.repository;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.avatar.Avatar;
import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AvatarQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AvatarQueryRepository avatarQueryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private AuthRepository authRepository;


    @DisplayName("멤버Id를 활용해 Avatar 조회")
    @Test
    void findByMemberId() throws Exception{
        //given
        Member member = createMember();
        Avatar avatar = createAvatar(member, Emotion.HAPPINESS, "행복");
        //when
        Avatar foundAvatar = avatarQueryRepository.findByMemberId(member.getId());

        //then
        assertThat(avatar.getMember()).isEqualTo(foundAvatar.getMember());
        assertThat(avatar.getEmotion()).isEqualTo(foundAvatar.getEmotion());
        assertThat(avatar.getQuestion()).isEqualTo(foundAvatar.getQuestion());
    }

    @DisplayName("ProviderId를 활용해 Avatar 조회")
    @Test
    void findByProviderId() throws Exception{
        //given
        Member member = createMember();
        Auth auth = createAuth(member);
        Avatar avatar = createAvatar(member, Emotion.HAPPINESS, "행복");
        //when
        Avatar foundAvatar = avatarQueryRepository.findByProviderId(auth.getProviderId());

        //then
        assertThat(avatar.getMember()).isEqualTo(foundAvatar.getMember());
        assertThat(avatar.getEmotion()).isEqualTo(foundAvatar.getEmotion());
        assertThat(avatar.getQuestion()).isEqualTo(foundAvatar.getQuestion());
    }


    private Member createMember() {
        Member member = Member.builder()
            .nickname("테스터")
            .gender(Gender.M)
            .birth("1923-07-13")
            .roleType(RoleType.USER)
            .build();
        return memberRepository.save(member);
    }

    private Avatar createAvatar(Member member, Emotion emotion, String question) {
        Avatar avatar = Avatar.builder()
            .member(member)
            .emotion(emotion)
            .question(question)
            .build();
        return avatarRepository.save(avatar);
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
}