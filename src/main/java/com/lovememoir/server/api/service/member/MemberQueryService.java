package com.lovememoir.server.api.service.member;

import com.lovememoir.server.api.controller.member.response.MemberResponse;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberQueryRepository memberQueryRepository;

    public MemberResponse searchMember(String providerId) {
        Member member = memberQueryRepository.findByProviderId(providerId);
        //TODO MemberQueryRepository 에서 직접 MemberResponse를 생성...??
        // 내부로직에서 쓸 떄엔 어떻게 구분하지?
        return MemberResponse.builder()
            .nickname(member.getNickname())
            .gender(member.getGender().toString())
            .birth(member.getBirth())
            .build();
    }
}
