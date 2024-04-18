package com.lovememoir.server.api.service.avatar;


import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import com.lovememoir.server.api.service.RedisService;
import com.lovememoir.server.common.auth.SecurityUtils;
import com.lovememoir.server.domain.avatar.Avatar;
import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.avatar.repository.AvatarRepository;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import com.lovememoir.server.domain.diaryanalysis.repository.DiaryAnalysisQueryRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import com.lovememoir.server.domain.question.Question;
import com.lovememoir.server.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.lovememoir.server.common.message.ExceptionMessage.NO_SUCH_MEMBER;

@RequiredArgsConstructor
@Service
@Transactional
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final MemberRepository memberRepository;
    private final DiaryAnalysisQueryRepository diaryAnalysisQueryRepository;
    private final QuestionRepository questionRepository;
    private final RedisService redisService;

    public AvatarResponse createAvatar() {

        // 회원가입 시 최초 아바타를 생성한다.
        Member member = getMember();
        Emotion emotion = Emotion.STABILITY;
        String question = "만나서 반가워요";

        Avatar avatar = Avatar.create(emotion, question, member);
        return AvatarResponse.of(avatar);

    }

    public AvatarRefreshResponse refreshAvatar() {

        Member member = getMember();
        Long memberId = member.getId();

        Map.Entry<Integer, Double> highestEmotion = getRecentHighestEmotion(member);
        Integer emotionCode = highestEmotion.getKey();
        Emotion emotion = Emotion.fromCode(emotionCode);

        //question들을 어떻게 할 것인가...
        Question selectedQuestion = getQuestion(emotion, memberId);
        // Avatar와 Question의 관계는 끊는 게 맞을까?
        Avatar avatar = Avatar.create(emotion, selectedQuestion.getContent(), member);

        return AvatarRefreshResponse.of(avatar);
    }

    private Question getQuestion(Emotion emotion, Long memberId) {
        List<Question> allQuestions = questionRepository.findByEmotion(emotion);
        List<Question> availableQuestions = allQuestions.stream()
            .filter(question -> redisService.find(redisService.generateAvatarKey(memberId, question.getId())) == null)
            .toList();
        Question selectedQuestion;
        if (availableQuestions.isEmpty()) {
            selectedQuestion = Question.builder()
                .emotion(Emotion.STABILITY)
                .content("행복하길 바래요")
                .build();
        } else {
            selectedQuestion = availableQuestions.get(new Random().nextInt(availableQuestions.size()));
        }
        redisService.saveWith7DaysExpiration(redisService.generateAvatarKey(memberId, selectedQuestion.getId()), "AA");
        return selectedQuestion;
    }

    private Map.Entry<Integer, Double> getRecentHighestEmotion(Member member) {
        List<DiaryAnalysis> analysisList = diaryAnalysisQueryRepository.findTop3RecentAnalysesByMemberId(member.getId());

        Map<Integer, Double> emotionScores = analysisList.stream()
            .collect(Collectors.groupingBy(DiaryAnalysis::getEmotionCode,
                Collectors.averagingDouble(DiaryAnalysis::getWeight)));

        return Collections.max(emotionScores.entrySet(), Map.Entry.comparingByValue());
    }

    private Member getMember() {
        String providerId = SecurityUtils.getProviderId();
        return memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));
    }

    private String generateQuestion() {

        //TODO 일기분석 토대로 질문 생성
        // 질문은 1일 1질문으로 제공..
        // 질문은 어디에 저장할지, 어떻게 제공여부 파악할지..(redis?)


        return "오늘은 무슨 일이 있었나요?";
    }
}
