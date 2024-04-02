package com.lovememoir.server.api.service.member;

import com.lovememoir.server.api.controller.member.response.MemberCreateResponse;
import com.lovememoir.server.api.controller.member.response.MemberModifyResponse;
import com.lovememoir.server.api.service.member.request.MemberCreateServiceRequest;
import com.lovememoir.server.api.service.member.request.MemberModifyServiceRequest;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.repository.AuthQueryRepository;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberQueryRepository;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

import static com.lovememoir.server.api.service.member.MemberValidator.*;
import static com.lovememoir.server.common.message.ExceptionMessage.ALREADY_REGISTERED_USER;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final AuthQueryRepository authQueryRepository;

    public MemberCreateResponse createMember(MemberCreateServiceRequest request) {

        Auth auth = authQueryRepository.findByProviderId(request.getProviderId());
        String nickname = validateNickname(request.getNickname());
        Gender gender = Gender.valueOf(request.getGender());

        Member byAuthId = memberQueryRepository.findByAuthId(auth.getId());
        if (byAuthId != null) {
            throw new IllegalArgumentException(ALREADY_REGISTERED_USER);
        }

        Member member = Member.builder()
            .nickname(nickname)
            .email(request.getEmail())
            .gender(gender)
            .birth(request.getBirth())
            .roleType(RoleType.USER)
            .auth(auth)
            .build();

        Member createdMember = memberRepository.save(member);
        return MemberCreateResponse.of(createdMember);
    }

    public MemberModifyResponse modifyMember(MemberModifyServiceRequest request) {
        Member member = memberQueryRepository.findByAuthId(request.getMember().getId());

        String nickname = validateNickname(request.getNickname());
        String birth = request.getBirth();
        Gender gender = Gender.valueOf(request.getGender());

        member.modify(nickname, birth, gender);

        return MemberModifyResponse.of(member);
    }
}

