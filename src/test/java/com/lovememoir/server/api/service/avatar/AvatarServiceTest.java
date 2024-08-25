package com.lovememoir.server.api.service.avatar;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.avatar.Avatar;
import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.avatar.repository.AvatarRepository;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.lovememoir.server.domain.avatar.Emotion.SADNESS;
import static com.lovememoir.server.domain.avatar.Emotion.STABILITY;
import static org.assertj.core.api.Assertions.assertThat;

class AvatarServiceTest extends IntegrationTestSupport {

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private AvatarRepository avatarRepository;

    @DisplayName("아바타 생성 성공")
    @Test
    void createAvatar() throws Exception{
        //given
        Member member = createMember();

        //when
        AvatarResponse response = avatarService.createAvatar(member);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getEmotion()).isEqualTo(STABILITY);
        assertThat(response.getQuestion()).isEqualTo("만나서 반가워요!");
    }


    @DisplayName("아바타 갱신 성공")
//    @Test
    void refreshAvatar() throws Exception{
        //given
        Member member = createMember();
        Auth auth = createAuth(member);

        Emotion emotion = Emotion.SADNESS;
        String question = "테스트입니다.";
        Avatar avatar = createMockAvatar(member, emotion, question);

        //when
        AvatarRefreshResponse response = avatarService.refreshAvatar(auth.getProviderId());

        //then
        assertThat(response).isNotNull();
        assertThat(response.getEmotion()).isEqualTo(SADNESS);
//        assertThat(response.getEmotion()).isNotEqualTo(emotion);
        assertThat(response.getQuestion()).isNotEqualTo(question);
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

    private Avatar createMockAvatar(Member member, Emotion emotion, String question) {
        Avatar avatar = Avatar.builder()
            .emotion(emotion)
            .question(question)
            .member(member)
            .build();
        avatarRepository.save(avatar);
        return avatar;
    }
}