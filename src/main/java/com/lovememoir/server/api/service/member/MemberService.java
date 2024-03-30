package com.lovememoir.server.api.service.member;

import com.lovememoir.server.api.controller.member.response.MemberCreateResponse;
import com.lovememoir.server.api.service.member.request.MemberCreateServiceRequest;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lovememoir.server.api.service.member.MemberValidator.*;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberCreateResponse createMember(MemberCreateServiceRequest request){
        String nickname = validateNickname(request.getNickname());
        Gender gender = Gender.valueOf(request.getGender());

//        Member member = saveMember(nickname, gender, request.getBirth());

//        return MemberCreateResponse.of(member);
        return null;
    }

//    private Member saveMember(String nickname, Gender gender, String birth) {
//        Member member = Member.create(nickname, gender, birth);
//        return memberRepository.save(member);
//    }
}

