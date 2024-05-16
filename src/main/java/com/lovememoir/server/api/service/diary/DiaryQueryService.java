package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.domain.diary.repository.DiaryQueryRepository;
import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static com.lovememoir.server.common.message.ExceptionMessage.NO_SUCH_MEMBER;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DiaryQueryService {

    private final DiaryQueryRepository diaryQueryRepository;
    private final MemberRepository memberRepository;

    public List<DiarySearchResponse> searchDiaries(final String providerId) {
        Member member = memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));
        List<DiarySearchResponse> content = diaryQueryRepository.findAllByMemberId(member.getId(), true);
        List<DiarySearchResponse> diaryContents = diaryQueryRepository.findAllByMemberId(member.getId(), false);

        if (content.isEmpty()) {
            return diaryContents;
        }

        content.addAll(diaryContents);
        return content;
    }

    public List<DiarySearchResponse> searchStoreDiaries(final String providerId) {
        Member member = memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));

        return diaryQueryRepository.findStoreAllByMemberId(member.getId());
    }
}
