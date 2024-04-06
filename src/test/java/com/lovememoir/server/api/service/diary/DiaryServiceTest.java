package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.api.FileStore;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryRemoveResponse;
import com.lovememoir.server.api.service.diary.request.DiaryCreateServiceRequest;
import com.lovememoir.server.api.service.diary.request.DiaryModifyServiceRequest;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.UploadFile;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.lovememoir.server.common.message.ExceptionMessage.MAXIMUM_DIARY_COUNT;
import static com.lovememoir.server.common.message.ExceptionMessage.NO_AUTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 1, 1, 0, 0);

        Member member = createMember();
        Auth auth = createAuth(member);

        DiaryCreateServiceRequest request = DiaryCreateServiceRequest.builder()
            .title("푸바오")
            .isLove(true)
            .startedDate(LocalDate.of(2023, 12, 25))
            .finishedDate(null)
            .build();

        //when
        DiaryCreateResponse response = diaryService.createDiary(auth.getProviderId(), currentDateTime, request);

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
            .memberKey(UUID.randomUUID().toString())
            .nickname("아이바오")
            .gender(Gender.F)
            .birth("2013-07-13")
            .roleType(RoleType.USER)
            .build();
        return memberRepository.save(member);
    }

    private Auth createAuth(Member member) {
        Auth auth = Auth.builder()
            .provider(ProviderType.KAKAO)
            .providerId("1234567890")
            .accessToken("access.token")
            .refreshToken("refresh.token")
            .expiredAt(null)
            .member(member)
            .build();
        return authRepository.save(auth);
    }
}