package com.lovememoir.server.api.service.avatar;


import com.lovememoir.server.api.controller.avatar.response.AvatarCreateResponse;
import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import com.lovememoir.server.common.auth.SecurityUtils;
import com.lovememoir.server.domain.avatar.Avatar;
import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.avatar.repository.AvatarRepository;
import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import com.lovememoir.server.domain.diaryanalysis.repository.DiaryAnalysisQueryRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static com.lovememoir.server.common.message.ExceptionMessage.NO_SUCH_MEMBER;

@RequiredArgsConstructor
@Service
@Transactional
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final MemberRepository memberRepository;
    private final DiaryAnalysisQueryRepository diaryAnalysisQueryRepository;

    public AvatarCreateResponse createAvatar() {

        Member member = getMember();

        Map.Entry<Integer, Double> highestEmotion = getRecentHighestEmotion(member);
        Integer emotionCode = highestEmotion.getKey();
        Double emotionWeight = highestEmotion.getValue();

        // 일주일 간의 기록을 분석하여 Avatar 생성

        Avatar avatar = Avatar.builder()
            .emotion(Emotion.STABILITY)
            .question(generateQuestion())
            .build();
        return null;

    }

    public AvatarRefreshResponse refreshAvatar() {
        return AvatarRefreshResponse.builder()
            .emotion("H2")
            .question("오늘은 무슨 일이 있었나요?")
            .build();
    }

    private Map.Entry<Integer, Double> getRecentHighestEmotion(Member member) {
        List<DiaryAnalysis> analysisList = diaryAnalysisQueryRepository.findTop3RecentAnalysesByMemberId(member.getId());

        Map<Integer, Double> emotionScores = analysisList.stream()
            .collect(Collectors.groupingBy(DiaryAnalysis::getEmotionCode,
                Collectors.averagingDouble(DiaryAnalysis::getWeight)));

        Map.Entry<Integer, Double> highestEmotion = Collections.max(emotionScores.entrySet(), Map.Entry.comparingByValue());
        return highestEmotion;
    }

    private Member getMember() {
        String providerId = SecurityUtils.getProviderId();
        Member member = memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));
        return member;
    }

    private String generateQuestion() {

        //TODO 일기분석 토대로 질문 생성
        // 질문은 1일 1질문으로 제공..
        // 질문은 어디에 저장할지, 어떻게 제공여부 파악할지..(redis?)


        return "오늘은 무슨 일이 있었나요?";
    }
}
