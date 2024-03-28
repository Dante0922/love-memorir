package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.api.FileStore;
import com.lovememoir.server.api.controller.diary.response.DiaryCreateResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryModifyResponse;
import com.lovememoir.server.api.controller.diary.response.DiaryRemoveResponse;
import com.lovememoir.server.api.service.diary.request.DiaryCreateServiceRequest;
import com.lovememoir.server.api.service.diary.request.DiaryModifyServiceRequest;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.UploadFile;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.member.Gender;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.Role;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

class DiaryServiceTest extends IntegrationTestSupport {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @MockBean
    private FileStore fileStore;

    @DisplayName("신규 일기장 등록 시 생성 가능한 일기장 최대 갯수를 초과하면 예외가 발생한다.")
    @Test
    void createDiaryWithMaxDiary() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 1, 0, 0);

        Member member = createMember();
        Diary diary1 = createDiary(member);
        Diary diary2 = createDiary(member);
        Diary diary3 = createDiary(member);

        DiaryCreateServiceRequest request = DiaryCreateServiceRequest.builder()
            .title("러바오")
            .isInLove(true)
            .relationshipStartedDate(LocalDate.of(2016, 3, 3))
            .build();

        //when //then
        assertThatThrownBy(() -> diaryService.createDiary(member.getMemberKey(), currentDateTime, request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage(MAXIMUM_DIARY_COUNT);

        List<Diary> diaries = diaryRepository.findAll();
        assertThat(diaries).hasSize(3);
    }

    @DisplayName("회원 고유키와 일기장 정보를 입력 받아 신규 일기장을 등록한다.")
    @Test
    void createDiary() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 1, 0, 0);

        Member member = createMember();
        Diary diary1 = createDiary(member);
        Diary diary2 = createDiary(member);

        DiaryCreateServiceRequest request = DiaryCreateServiceRequest.builder()
            .title("러바오")
            .isInLove(true)
            .relationshipStartedDate(LocalDate.of(2016, 3, 3))
            .build();

        //when
        DiaryCreateResponse response = diaryService.createDiary(member.getMemberKey(), currentDateTime, request);

        //then
        assertThat(response).isNotNull();

        Optional<Diary> findDiary = diaryRepository.findById(response.getDiaryId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get())
            .hasFieldOrPropertyWithValue("isFixed", false)
            .hasFieldOrPropertyWithValue("title", "러바오와의 연애 기록")
            .hasFieldOrPropertyWithValue("relationshipStartedDate", LocalDate.of(2016, 3, 3))
            .hasFieldOrPropertyWithValue("pageCount", 0);
    }

    @DisplayName("일기장 수정시 본인의 일기장이 아니라면 예외가 발생한다.")
    @Test
    void modifyDiaryWithoutAuth() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 1, 0, 0);

        Member member = createMember();
        Diary diary = createDiary(member);

        Member otherMember = createMember();

        DiaryModifyServiceRequest request = DiaryModifyServiceRequest.builder()
            .title("루이바오")
            .isInLove(true)
            .relationshipStartedDate(LocalDate.of(2023, 7, 7))
            .build();

        //when //then
        assertThatThrownBy(() -> diaryService.modifyDiary(otherMember.getMemberKey(), diary.getId(), currentDateTime, request))
            .isInstanceOf(AuthException.class)
            .hasMessage(NO_AUTH);
    }

    @DisplayName("회원 고유키와 일기장 정보를 입력 받아 일기장을 수정한다.")
    @Test
    void modifyDiary() {
        //given
        LocalDateTime currentDateTime = LocalDateTime.of(2024, 3, 1, 0, 0);

        Member member = createMember();
        Diary diary = createDiary(member);

        DiaryModifyServiceRequest request = DiaryModifyServiceRequest.builder()
            .title("루이바오")
            .isInLove(true)
            .relationshipStartedDate(LocalDate.of(2023, 7, 7))
            .build();

        //when
        DiaryModifyResponse response = diaryService.modifyDiary(member.getMemberKey(), diary.getId(), currentDateTime, request);

        //then
        assertThat(response).isNotNull();

        Optional<Diary> findDiary = diaryRepository.findById(diary.getId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get())
            .hasFieldOrPropertyWithValue("isFixed", false)
            .hasFieldOrPropertyWithValue("title", "루이바오와의 연애 기록")
            .hasFieldOrPropertyWithValue("relationshipStartedDate", LocalDate.of(2023, 7, 7))
            .hasFieldOrPropertyWithValue("pageCount", 0);
    }

    @DisplayName("일기장 이미지 수정시 본인의 일기장이 아니라면 예외가 발생한다.")
    @Test
    void modifyDiaryImageWithoutAuth() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);

        Member otherMember = createMember();

        MockMultipartFile file = new MockMultipartFile(
            "upload-image",
            "diary-profile-upload-image.jpg",
            "image/jpg",
            "image data".getBytes()
        );

        //when //then
        assertThatThrownBy(() -> diaryService.modifyDiaryImage(otherMember.getMemberKey(), diary.getId(), file))
            .isInstanceOf(AuthException.class)
            .hasMessage(NO_AUTH);
    }

    @DisplayName("회원 고유키와 일기장 정보를 입력 받아 이미지를 수정한다.")
    @Test
    void modifyDiaryImage() throws IOException {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);

        MockMultipartFile file = new MockMultipartFile(
            "upload-image",
            "diary-profile-upload-image.jpg",
            "image/jpg",
            "image data".getBytes()
        );

        UploadFile uploadFile = UploadFile.builder()
            .uploadFileName("diary-profile-upload-image.jpg")
            .storeFileUrl(UUID.randomUUID().toString())
            .build();

        given(fileStore.storeFile(any()))
            .willReturn(uploadFile);

        //when
        DiaryModifyResponse response = diaryService.modifyDiaryImage(member.getMemberKey(), diary.getId(), file);

        //then
        assertThat(response).isNotNull();

        Optional<Diary> findDiary = diaryRepository.findById(diary.getId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get())
            .hasFieldOrPropertyWithValue("file.uploadFileName", "diary-profile-upload-image.jpg")
            .hasFieldOrPropertyWithValue("file.storeFileUrl", uploadFile.getStoreFileUrl());
    }

    @DisplayName("일기장 삭제시 본인의 일기장이 아니라면 예외가 발생한다.")
    @Test
    void removeDiaryWithoutAuth() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);

        Member otherMember = createMember();

        //when //then
        assertThatThrownBy(() -> diaryService.removeDiary(otherMember.getMemberKey(), diary.getId()))
            .isInstanceOf(AuthException.class)
            .hasMessage(NO_AUTH);
    }

    @DisplayName("회원 고유키와 일기장 식별키를 입력 받아 일기장을 삭제한다.")
    @Test
    void removeDiary() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);

        //when
        DiaryRemoveResponse response = diaryService.removeDiary(member.getMemberKey(), diary.getId());

        //then
        assertThat(response).isNotNull();

        Optional<Diary> findDiary = diaryRepository.findById(diary.getId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get().isDeleted()).isTrue();
    }

    private Member createMember() {
        Member member = Member.builder()
            .memberKey(UUID.randomUUID().toString())
            .nickname("아이바오")
            .gender(Gender.F)
            .birth("2013-07-13")
            .role(Role.USER)
            .build();
        return memberRepository.save(member);
    }

    private Diary createDiary(Member member) {
        Diary diary = Diary.builder()
            .isFixed(false)
            .title("러바오와의 연애 기록")
            .isInLove(true)
            .relationshipStartedDate(LocalDate.of(2016, 3, 3))
            .pageCount(0)
            .member(member)
            .build();
        return diaryRepository.save(diary);
    }
}