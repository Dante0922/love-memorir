package com.lovememoir.server.api.service.member;


import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.api.controller.member.response.MemberCreateResponse;
import com.lovememoir.server.api.service.member.request.MemberCreateServiceRequest;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

public class MemberServiceTest extends IntegrationTestSupport {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


//    @DisplayName("정상 회원 가입")
//    @Test
//    void createMember() throws Exception {
//        //given
//        MemberCreateServiceRequest request = MemberCreateServiceRequest.builder()
//            .nickname("루이바오")
//            .gender("F")
//            .birth("2016-03-03")
//            .build();
//        //when
//        MemberCreateResponse response = memberService.createMember(request);
//
//        //then
//        assertThat(response).isNotNull();
//
//        Member findMember = memberRepository.findByMemberKey(response.getMemberKey())
//            .orElseThrow(() -> new RuntimeException("회원 가입이 정상 처리되지 않았습니다."));
//
//        assertThat(findMember.getNickname()).isEqualTo("루이바오");
//        assertThat(findMember.getGender()).isEqualTo(Gender.F);
//        assertThat(findMember.getBirth()).isEqualTo("2016-03-03");
//    }
}
