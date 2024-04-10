package com.lovememoir.server.api.service.member;

import com.lovememoir.server.api.controller.member.response.MemberCreateResponse;
import com.lovememoir.server.api.controller.member.response.MemberModifyResponse;
import com.lovememoir.server.api.controller.member.response.MemberRemoveResponse;
import com.lovememoir.server.api.service.member.request.MemberCreateServiceRequest;
import com.lovememoir.server.api.service.member.request.MemberModifyServiceRequest;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.repository.AuthQueryRepository;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberQueryRepository;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lovememoir.server.api.service.member.MemberValidator.validateNickname;
import static com.lovememoir.server.common.message.ExceptionMessage.ALREADY_REGISTERED_USER;
import static com.lovememoir.server.common.message.ExceptionMessage.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final AuthQueryRepository authQueryRepository;
    private final AuthRepository authRepository;

    public MemberCreateResponse createMember(MemberCreateServiceRequest request) {
        String nickname = validateNickname(request.getNickname());
        Gender gender = Gender.valueOf(request.getGender());
        Auth currentAuth = authQueryRepository.findByProviderId(request.getProviderId());

        validateDuplicateMember(request.getProviderId());

        Member savedMember = saveMember(nickname, gender, request.getBirth(), RoleType.USER, currentAuth);

        return MemberCreateResponse.of(savedMember);
    }

    public MemberModifyResponse modifyMember(MemberModifyServiceRequest request) {
        Auth currentAuth = authQueryRepository.findByProviderId(request.getProviderId());
        Member member = validateMemberExistence(request.getProviderId());

        String nickname = validateNickname(request.getNickname());
        String birth = request.getBirth();
        Gender gender = Gender.valueOf(request.getGender());

        member.modify(nickname, birth, gender);

        return MemberModifyResponse.of(member);
    }

    public MemberRemoveResponse removeMember(String providerId) {
        Member member = validateMemberExistence(providerId);
        member.remove();
        return MemberRemoveResponse.of(member);
    }

    private void validateDuplicateMember(String providerId) {
        Member member = memberQueryRepository.findByProviderId(providerId);
        if (member != null) {
            throw new IllegalArgumentException(ALREADY_REGISTERED_USER);
        }
    }

    private Member validateMemberExistence(String providerId) {
        Member member = memberQueryRepository.findByProviderId(providerId);
        if (member == null) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        return member;
    }


    private Member saveMember(String nickname, Gender gender, String birth, RoleType roleType, Auth auth) {
        final Member member = Member.builder()
            .nickname(nickname)
            .gender(gender)
            .birth(birth)
            .roleType(roleType)
            .auth(auth)
            .build();
        return memberRepository.save(member);

    }
}

