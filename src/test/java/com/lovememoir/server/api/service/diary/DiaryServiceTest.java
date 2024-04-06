package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.api.FileStore;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.service.diary.request.DiaryCreateServiceRequest;
import com.lovememoir.server.api.service.diary.request.DiaryModifyServiceRequest;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.LoveInfo;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Optional;

import static com.lovememoir.server.common.message.ExceptionMessage.NO_AUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

class DiaryServiceTest extends IntegrationTestSupport {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private AuthRepository authRepository;

    @MockBean
    private FileStore fileStore;

    @DisplayName("회원 정보와 일기장 정보를 입력 받아 신규 일기장을 등록한다.")
    @Test
    void createDiary() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 1);

        Member member = createMember();
        Auth auth = createAuth(member, "1234567890");

        DiaryCreateServiceRequest request = DiaryCreateServiceRequest.builder()
            .title("푸바오")
            .isLove(true)
            .startedDate(LocalDate.of(2023, 12, 25))
            .finishedDate(null)
            .build();

        //when
        DiaryCreateResponse response = diaryService.createDiary(auth.getProviderId(), currentDate, request);

        //then
        assertThat(response).isNotNull();

        Optional<Diary> findDiary = diaryRepository.findById(response.getDiaryId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get())
            .hasFieldOrPropertyWithValue("isMain", false)
            .hasFieldOrPropertyWithValue("title", "푸바오")
            .hasFieldOrPropertyWithValue("loveInfo.isLove", true)
            .hasFieldOrPropertyWithValue("loveInfo.startedDate", LocalDate.of(2023, 12, 25))
            .hasFieldOrPropertyWithValue("loveInfo.finishedDate", null)
            .hasFieldOrPropertyWithValue("pageCount", 0)
            .hasFieldOrPropertyWithValue("profile.uploadFileName", null)
            .hasFieldOrPropertyWithValue("profile.storeFileUrl", null)
            .hasFieldOrPropertyWithValue("isStored", false);
    }

    @DisplayName("일기장 정보 수정 시 본인의 일기장이 아니라면 예외가 발생한다.")
    @Test
    void modifyDiaryWithoutAuth() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 1);

        Member member = createMember();
        Auth auth = createAuth(member, "1234567890");
        Diary diary = createDiary(member);

        DiaryModifyServiceRequest request = DiaryModifyServiceRequest.builder()
            .title("푸바오")
            .isLove(true)
            .startedDate(LocalDate.of(2023, 12, 25))
            .finishedDate(null)
            .build();

        Member otherMember = createMember();
        Auth otherAuth = createAuth(otherMember, "0987654321");

        //when //then
        assertThatThrownBy(() -> diaryService.modifyDiary(otherAuth.getProviderId(), diary.getId(), currentDate, request))
            .isInstanceOf(AuthException.class)
            .hasMessage(NO_AUTH);
    }

    @DisplayName("회원 정보와 일기장 정보를 입력 받아 일기장 정보를 수정한다.")
    @Test
    void modifyDiary() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 1, 1);

        Member member = createMember();
        Auth auth = createAuth(member, "1234567890");
        Diary diary = createDiary(member);

        DiaryModifyServiceRequest request = DiaryModifyServiceRequest.builder()
            .title("푸바오")
            .isLove(true)
            .startedDate(LocalDate.of(2023, 12, 25))
            .finishedDate(null)
            .build();

        //when
        DiaryModifyResponse response = diaryService.modifyDiary(auth.getProviderId(), diary.getId(), currentDate, request);

        //then
        assertThat(response).isNotNull();

        Optional<Diary> findDiary = diaryRepository.findById(response.getDiaryId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get())
            .hasFieldOrPropertyWithValue("isMain", false)
            .hasFieldOrPropertyWithValue("title", "푸바오")
            .hasFieldOrPropertyWithValue("loveInfo.isLove", true)
            .hasFieldOrPropertyWithValue("loveInfo.startedDate", LocalDate.of(2023, 12, 25))
            .hasFieldOrPropertyWithValue("loveInfo.finishedDate", null)
            .hasFieldOrPropertyWithValue("pageCount", 0)
            .hasFieldOrPropertyWithValue("profile.uploadFileName", null)
            .hasFieldOrPropertyWithValue("profile.storeFileUrl", null)
            .hasFieldOrPropertyWithValue("isStored", false);
    }

    private Member createMember() {
        Member member = Member.builder()
            .nickname("아이바오")
            .gender(Gender.F)
            .birth("2013-07-13")
            .roleType(RoleType.USER)
            .build();
        return memberRepository.save(member);
    }

    private Auth createAuth(Member member, String providerId) {
        Auth auth = Auth.builder()
            .provider(ProviderType.KAKAO)
            .providerId(providerId)
            .accessToken("access.token")
            .refreshToken("refresh.token")
            .expiredAt(null)
            .member(member)
            .build();
        return authRepository.save(auth);
    }

    private Diary createDiary(Member member) {
        Diary diary = Diary.builder()
            .isDeleted(false)
            .isMain(false)
            .title("후이바오")
            .loveInfo(LoveInfo.builder()
                .isLove(false)
                .startedDate(null)
                .finishedDate(null)
                .build())
            .pageCount(0)
            .profile(null)
            .isStored(false)
            .member(member)
            .build();
        return diaryRepository.save(diary);
    }
}