package com.lovememoir.server.api.service.member;

import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberQueryRepository;
import com.lovememoir.server.domain.member.repository.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.lovememoir.server.common.message.ExceptionMessage.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    public MemberResponse searchMember(String providerId) {
        Member member = validateAndGetMember(providerId);
        //TODO MemberQueryRepository 에서 직접 MemberResponse를 생성...??
        // 내부로직에서 쓸 떄엔 어떻게 구분하지?
        return MemberResponse.builder()
            .nickname(member.getNickname())
            .gender(member.getGender().toString())
            .birth(member.getBirth())
            .build();
    }
    private Member validateAndGetMember(String providerId) {
        Member member = memberQueryRepository.findByProviderId(providerId);
        if (member == null) {
            throw new IllegalArgumentException(USER_NOT_FOUND);
        }
        return member;
    }

}
