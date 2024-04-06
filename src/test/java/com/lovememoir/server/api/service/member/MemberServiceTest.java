package com.lovememoir.server.api.service.member;


import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.WithAuthUser;
import com.lovememoir.server.api.controller.auth.response.KakaoUserResponse;
import com.lovememoir.server.api.controller.member.request.MemberModifyRequest;
import com.lovememoir.server.api.controller.member.response.MemberCreateResponse;
import com.lovememoir.server.api.controller.member.response.MemberModifyResponse;
import com.lovememoir.server.api.service.member.request.MemberCreateServiceRequest;
import com.lovememoir.server.api.service.member.request.MemberModifyServiceRequest;
import com.lovememoir.server.common.auth.SecurityUtils;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberQueryRepository;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import groovy.util.logging.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.lovememoir.server.common.message.ExceptionMessage.ALREADY_REGISTERED_USER;
import static com.lovememoir.server.common.message.ExceptionMessage.USER_NOT_FOUND;
import static com.lovememoir.server.common.message.ValidationMessage.INVALID_NICKNAME_PATTERN;
import static com.lovememoir.server.common.message.ValidationMessage.MAX_LENGTH_NICKNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberQueryRepository memberQueryRepository;

    @Autowired
    private AuthRepository authRepository;


    @DisplayName("정상 회원 가입")
    @Test
    @WithAuthUser(providerId = "providerId", roles = "USER")
    void createMember() throws Exception {
        //given
        Auth auth = createAuth("providerId");
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .nickname("루이바오")
            .gender("F")
            .birth("2016-03-03")
            .providerId("providerId")
            .build();

        //when
        MemberCreateResponse response = memberService.createMember(request);

        //then
        AssertionsForClassTypes.assertThat(response).isNotNull();
        AssertionsForClassTypes.assertThat(SecurityUtils.getProviderId()).isEqualTo("providerId");


        Member savedMember = memberQueryRepository.findByProviderId("providerId");
        AssertionsForClassTypes.assertThat(savedMember.getNickname()).isEqualTo("루이바오");
        AssertionsForClassTypes.assertThat(savedMember.getGender()).isEqualTo(Gender.F);
        AssertionsForClassTypes.assertThat(savedMember.getBirth()).isEqualTo("2016-03-03");
    }

    @DisplayName("회원의 닉네임은 8자를 초과할 수 없다.")
    @Test
    @WithAuthUser(providerId = "providerId", roles = "USER")
    void createMemberWithInvalidNicknameLength() throws Exception {
        //given

        Auth auth1 = createAuth("test1");
        Auth auth2 = createAuth("test2");
        Member member1 = createMember(auth1);
        Member member2 = createMember(auth2);

        Auth auth = createAuth("providerId");
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .nickname("heeleloloello")
            .gender("F")
            .birth("2016-03-03")
            .providerId("providerId")
            .build();


        //when //then
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(MAX_LENGTH_NICKNAME);


        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(2);
    }

    @DisplayName("회원의 닉네임은 한글과 숫자만 허용한다.")
    @Test
    @WithAuthUser(providerId = "providerId", roles = "USER")
    void createMemberWithInvalidNicknamePattern() throws Exception {
        //given

        Auth auth1 = createAuth("test1");
        Auth auth2 = createAuth("test2");
        Member member1 = createMember(auth1);
        Member member2 = createMember(auth2);

        Auth auth = createAuth("providerId");
        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .nickname("hello11")
            .gender("F")
            .birth("2016-03-03")
            .providerId("providerId")
            .build();


        //when //then
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(INVALID_NICKNAME_PATTERN);


        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(2);
    }

    @DisplayName("중복 가입은 불가하다.")
    @Test
    @WithAuthUser(providerId = "providerId", roles = "USER")
    void createMemberWithDuplicate() throws Exception {
        //given

        Auth auth = createAuth("providerId");
        Member member1 = createMember(auth);

        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
            .nickname("연해말")
            .gender("F")
            .birth("2016-03-03")
            .providerId("providerId")
            .build();


        //when //then
        assertThatThrownBy(() -> memberService.createMember(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(ALREADY_REGISTERED_USER);


        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("정상 회원 가입")
    @Test
    @WithAuthUser(providerId = "providerId", roles = "USER")
    void modifyMember() throws Exception {
        //given
        Auth auth = createAuth("providerId");
        Member member = createMember(auth);

        MemberModifyServiceRequest request = MemberModifyServiceRequest.builder()
            .nickname("수정")
            .gender("M")
            .birth("2024-04-06")
            .providerId("providerId")
            .build();

        //when
        MemberModifyResponse response = memberService.modifyMember(request);

        //then
        AssertionsForClassTypes.assertThat(response).isNotNull();
        AssertionsForClassTypes.assertThat(SecurityUtils.getProviderId()).isEqualTo("providerId");


        Member savedMember = memberQueryRepository.findByProviderId("providerId");
        AssertionsForClassTypes.assertThat(savedMember.getNickname()).isEqualTo("수정");
        AssertionsForClassTypes.assertThat(savedMember.getGender()).isEqualTo(Gender.M);
        AssertionsForClassTypes.assertThat(savedMember.getBirth()).isEqualTo("2024-04-06");
    }

    @DisplayName("[정보수정] 회원의 닉네임은 8자를 초과할 수 없다.")
    @Test
    @WithAuthUser(providerId = "providerId", roles = "USER")
    void modifyMemberWithInvalidNicknameLength() throws Exception {
        //given

        Auth auth = createAuth("providerId");
        Member member = createMember(auth);

        MemberModifyServiceRequest request = MemberModifyServiceRequest.builder()
            .nickname("수정2222222222")
            .gender("M")
            .birth("2024-04-06")
            .providerId("providerId")
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.modifyMember(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(MAX_LENGTH_NICKNAME);

        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("[정보수정] 회원의 닉네임은 한글과 숫자만 허용한다.")
    @Test
    @WithAuthUser(providerId = "providerId", roles = "USER")
    void modifyMemberWithInvalidNicknamePattern() throws Exception {

        //given
        Auth auth = createAuth("providerId");
        Member member = createMember(auth);

        MemberModifyServiceRequest request = MemberModifyServiceRequest.builder()
            .nickname("crystal@")
            .gender("M")
            .birth("2024-04-06")
            .providerId("providerId")
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.modifyMember(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(INVALID_NICKNAME_PATTERN);


        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(1);
    }

    @DisplayName("[정보수정] 가입하지 않은 유저의 정보는 수정할 수 없다.")
    @Test
    @WithAuthUser(providerId = "providerId", roles = "USER")
    void modifyMemberWithNotFoundUser() throws Exception {

        //given
        Auth auth1 = createAuth("test1");
        Auth auth2 = createAuth("test2");
        Member member1 = createMember(auth1);
        Member member2 = createMember(auth2);

        Auth auth = createAuth("providerId");

        MemberModifyServiceRequest request = MemberModifyServiceRequest.builder()
            .nickname("crystal@")
            .gender("M")
            .birth("2024-04-06")
            .providerId("providerId")
            .build();

        //when //then
        assertThatThrownBy(() -> memberService.modifyMember(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(USER_NOT_FOUND);


        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(2);
    }


    private Auth createAuth(String providerId) {
        Auth auth = Auth.builder()
            .provider(ProviderType.KAKAO)
            .providerId(providerId)
            .build();
        return authRepository.save(auth);
    }

    private Member createMember(Auth auth) {
        Member member = Member.builder()
            .nickname("루이바")
            .gender(Gender.F)
            .birth("2016-03-03")
            .roleType(RoleType.USER)
            .auth(auth)
            .build();
        return memberRepository.save(member);
    }
}
