package com.lovememoir.server.api.service.avatar;


import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import com.lovememoir.server.api.service.RedisService;
import com.lovememoir.server.domain.avatar.Avatar;
import com.lovememoir.server.domain.avatar.Emotion;
import com.lovememoir.server.domain.avatar.repository.AvatarQueryRepository;
import com.lovememoir.server.domain.avatar.repository.AvatarRepository;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import com.lovememoir.server.domain.diaryanalysis.repository.DiaryAnalysisQueryRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import com.lovememoir.server.domain.question.Question;
import com.lovememoir.server.domain.question.repository.QuestionQueryRepository;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.lovememoir.server.common.message.ExceptionMessage.NO_SUCH_MEMBER;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class AvatarService {
    private final MemberRepository memberRepository;
    private final AvatarRepository avatarRepository;
    private final AvatarQueryRepository avatarQueryRepository;
    private final QuestionQueryRepository questionQueryRepository;
    private final DiaryAnalysisQueryRepository diaryAnalysisQueryRepository;
    private final RedisService redisService;

    // TODO: 감정, 대화, 질문 로직 수정할 것.
    // TODO: 감정, 질문, 대화 코드화할 것.

    public AvatarResponse createAvatar(Member member) {

        Emotion emotion = Emotion.STABILITY;
        String question = "만나서 반가워요!";

        Avatar avatar = Avatar.create(emotion, question, member);
        avatarRepository.save(avatar);
        return AvatarResponse.of(avatar);
    }

    public AvatarRefreshResponse refreshAvatar(String providerId) {

        Member member = getMember(providerId);
        Long memberId = member.getId();
        Avatar avatar = avatarQueryRepository.findByMemberId(memberId);
        boolean isEmotionRefreshedToday = avatar.getEmotionModifiedDateTime().toLocalDate().isEqual(LocalDate.now());

        Avatar updatedAvatar = updateAvatarEmotionAndQuestion(memberId, avatar, isEmotionRefreshedToday);


        return AvatarRefreshResponse.of(avatar);
    }

    private Avatar updateAvatarEmotionAndQuestion(Long memberId, Avatar avatar, boolean isEmotionRefreshedToday) {
        Emotion emotion;
        Question question;
        if (isEmotionRefreshedToday) {
            emotion = avatar.getEmotion();
            question = getConversationByEmotion(memberId, emotion);
        } else {
            emotion = getRecentHighestEmotion(memberId);
            question = getQuestionByEmotion(memberId, emotion);
        }

        avatar.modified(emotion, question.getContent());

        return avatar;
    }

    private Emotion getRecentHighestEmotion(Long memberId) {
        List<DiaryAnalysis> analyses = diaryAnalysisQueryRepository.findTop3RecentAnalysesByMemberId(memberId)
            .orElse(Collections.emptyList());

        if (analyses.isEmpty()) {
            return Emotion.STABILITY;
        }

        Emotion emotion = analyses.stream()
            .collect(Collectors.groupingBy(DiaryAnalysis::getEmotionCode,
                Collectors.averagingDouble(DiaryAnalysis::getWeight)))
            .entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(entry -> Emotion.fromCode(entry.getKey()))
            .orElse(Emotion.STABILITY);

        return emotion;
    }

    private Question getQuestionByEmotion(Long memberId, Emotion emotion) {
        List<Question> allQuestions = questionQueryRepository.findQuestionByEmotion(emotion);
        List<Question> availableQuestions = filterAvailableQuestions(memberId, allQuestions);
        Question selectedQuestion = selectRandomQuestion(availableQuestions);
        saveQuestionInRedis(memberId, selectedQuestion);

        return selectedQuestion;
    }

    private Question getConversationByEmotion(Long memberId, Emotion emotion) {
        List<Question> allQuestions = questionQueryRepository.findConversationByEmotion(emotion);
        List<Question> availableQuestions = filterAvailableQuestions(memberId, allQuestions);
        Question selectedQuestion = selectRandomQuestion(allQuestions);
        saveQuestionInRedis(memberId, selectedQuestion);

        return selectedQuestion;
    }

    private List<Question> filterAvailableQuestions(Long memberId, List<Question> allQuestions) {
        return allQuestions.stream()
            .filter(question -> redisService.find(redisService.generateAvatarKey(memberId, question.getId())) == null)
            .toList();
    }

    private static Question selectRandomQuestion(List<Question> availableQuestions) {
        return availableQuestions.isEmpty() ?
            Question.builder().emotion(Emotion.STABILITY).content("항복하길 바래요").build() :
            availableQuestions.get(new Random().nextInt(availableQuestions.size()));
    }

    private void saveQuestionInRedis(Long memberId, Question selectedQuestion) {
        redisService.saveWith7DaysExpiration(redisService.generateAvatarKey(memberId, selectedQuestion.getId()), "AA");
    }

    private Member getMember(String providerId) {
        return memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));
    }
}
