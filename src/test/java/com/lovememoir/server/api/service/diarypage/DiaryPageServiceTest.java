package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.api.FileStore;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageCreateResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageModifyResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageRemoveResponse;
import com.lovememoir.server.api.service.diarypage.request.DiaryPageCreateServiceRequest;
import com.lovememoir.server.api.service.diarypage.request.DiaryPageModifyServiceRequest;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.attachedimage.AttachedImage;
import com.lovememoir.server.domain.attachedimage.repository.AttachedImageRepository;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.auth.ProviderType;
import com.lovememoir.server.domain.auth.repository.AuthRepository;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.LoveInfo;
import com.lovememoir.server.domain.diary.UploadFile;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diarypage.AnalysisResult;
import com.lovememoir.server.domain.diarypage.AnalysisStatus;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
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
import java.util.List;
import java.util.Optional;

import static com.lovememoir.server.common.message.ExceptionMessage.NO_AUTH;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class DiaryPageServiceTest extends IntegrationTestSupport {

    @Autowired
    private DiaryPageService diaryPageService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryPageRepository diaryPageRepository;

    @Autowired
    private AttachedImageRepository attachedImageRepository;

    @MockBean
    private FileStore fileStore;

    @DisplayName("신규 일기 등록 시 본인의 일기장이 아니라면 예외가 발생한다.")
    @Test
    void createDiaryPageWithoutAuth() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 4, 6);

        Member member = createMember();
        Auth auth = createAuth(member, "0123456789");
        Diary diary = createDiary(member, 0);
        MockMultipartFile image1 = new MockMultipartFile(
            "image",
            "diary-page-attached-image1.jpg",
            "image/jpg",
            "image data".getBytes()
        );
        MockMultipartFile image2 = new MockMultipartFile(
            "image",
            "diary-page-attached-image2.jpg",
            "image/jpg",
            "image data".getBytes()
        );

        DiaryPageCreateServiceRequest request = DiaryPageCreateServiceRequest.builder()
            .title("푸바오가 출국한 날")
            .content("푸바오가 오늘 중국으로 출국했다...")
            .recordDate(LocalDate.of(2024, 4, 3))
            .images(List.of(image1, image2))
            .build();

        Member otherMember = createMember();
        Auth otherAuth = createAuth(otherMember, "9876543210");

        //when //then
        assertThatThrownBy(() -> diaryPageService.createDiaryPage(otherAuth.getProviderId(), diary.getId(), currentDate, request))
            .isInstanceOf(AuthException.class)
            .hasMessage(NO_AUTH);

        List<DiaryPage> diaryPages = diaryPageRepository.findAll();
        assertThat(diaryPages).isEmpty();

        List<AttachedImage> attachedImages = attachedImageRepository.findAll();
        assertThat(attachedImages).isEmpty();

        Optional<Diary> findDiary = diaryRepository.findById(diary.getId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get().getPageCount()).isZero();
    }

    @DisplayName("회원 정보와 일기 정보를 입력 받아 신규 일기를 등록한다.")
    @Test
    void createDiaryPage() throws IOException {
        //given
        LocalDate currentDate = LocalDate.of(2024, 4, 6);

        Member member = createMember();
        Auth auth = createAuth(member, "0123456789");
        Diary diary = createDiary(member, 0);
        MockMultipartFile image1 = new MockMultipartFile(
            "image",
            "diary-page-attached-image1.jpg",
            "image/jpg",
            "image data".getBytes()
        );
        MockMultipartFile image2 = new MockMultipartFile(
            "image",
            "diary-page-attached-image2.jpg",
            "image/jpg",
            "image data".getBytes()
        );

        DiaryPageCreateServiceRequest request = DiaryPageCreateServiceRequest.builder()
            .title("푸바오가 출국한 날")
            .content("푸바오가 오늘 중국으로 출국했다...")
            .recordDate(LocalDate.of(2024, 4, 3))
            .images(List.of(image1, image2))
            .build();

        UploadFile uploadFile1 = UploadFile.builder()
            .uploadFileName("diary-page-attached-image1.jpg")
            .storeFileUrl("store-diary-page-attached-image1.jpg")
            .build();
        UploadFile uploadFile2 = UploadFile.builder()
            .uploadFileName("diary-page-attached-image2.jpg")
            .storeFileUrl("store-diary-page-attached-image2.jpg")
            .build();


        given(fileStore.storeFiles(request.getImages()))
            .willReturn(List.of(uploadFile1, uploadFile2));

        //when
        DiaryPageCreateResponse response = diaryPageService.createDiaryPage(auth.getProviderId(), diary.getId(), currentDate, request);

        //then
        assertThat(response).isNotNull();

        Optional<DiaryPage> findDiaryPage = diaryPageRepository.findById(response.getDiaryPageId());
        assertThat(findDiaryPage).isPresent();
        assertThat(findDiaryPage.get())
            .hasFieldOrPropertyWithValue("title", "푸바오가 출국한 날")
            .hasFieldOrPropertyWithValue("content", "푸바오가 오늘 중국으로 출국했다...")
            .hasFieldOrPropertyWithValue("recordDate", LocalDate.of(2024, 4, 3))
            .hasFieldOrPropertyWithValue("analysis.analysisStatus", AnalysisStatus.BEFORE)
            .hasFieldOrPropertyWithValue("analysis.emotionCode", null);

        List<AttachedImage> attachedImages = attachedImageRepository.findAll();
        assertThat(attachedImages).hasSize(2);

        Optional<Diary> findDiary = diaryRepository.findById(diary.getId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get().getPageCount()).isOne();
    }

    @DisplayName("일기 수정 시 본인의 일기장이 아니라면 예외가 발생한다.")
    @Test
    void modifyDiaryPageWithoutAuth() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 4, 6);

        Member member = createMember();
        Auth auth = createAuth(member, "0123456789");
        Diary diary = createDiary(member, 1);
        DiaryPage diaryPage = createDiaryPage(diary);

        DiaryPageModifyServiceRequest request = DiaryPageModifyServiceRequest.builder()
            .title("푸바오가 출국한 날")
            .content("푸바오가 오늘 중국으로 출국했다...")
            .recordDate(LocalDate.of(2024, 4, 3))
            .build();

        Member otherMember = createMember();
        Auth otherAuth = createAuth(otherMember, "9876543210");

        //when //then
        assertThatThrownBy(() -> diaryPageService.modifyDiaryPage(otherAuth.getProviderId(), diaryPage.getId(), currentDate, request))
            .isInstanceOf(AuthException.class)
            .hasMessage(NO_AUTH);
    }

    @DisplayName("회원 정보와 일기 정보를 입력 받아 일기를 수정한다.")
    @Test
    void modifyDiaryPage() {
        //given
        LocalDate currentDate = LocalDate.of(2024, 4, 6);

        Member member = createMember();
        Auth auth = createAuth(member, "0123456789");
        Diary diary = createDiary(member, 1);
        DiaryPage diaryPage = createDiaryPage(diary);

        DiaryPageModifyServiceRequest request = DiaryPageModifyServiceRequest.builder()
            .title("푸바오가 출국한 날")
            .content("푸바오가 오늘 중국으로 출국했다...")
            .recordDate(LocalDate.of(2024, 4, 3))
            .build();

        //when
        DiaryPageModifyResponse response = diaryPageService.modifyDiaryPage(auth.getProviderId(), diaryPage.getId(), currentDate, request);

        //then
        assertThat(response).isNotNull();

        Optional<DiaryPage> findDiaryPage = diaryPageRepository.findById(response.getDiaryPageId());
        assertThat(findDiaryPage).isPresent();
        assertThat(findDiaryPage.get())
            .hasFieldOrPropertyWithValue("title", "푸바오가 출국한 날")
            .hasFieldOrPropertyWithValue("content", "푸바오가 오늘 중국으로 출국했다...")
            .hasFieldOrPropertyWithValue("recordDate", LocalDate.of(2024, 4, 3));
    }

    @DisplayName("일기 식별키 목록을 입력 받아 일기를 삭제한다.")
    @Test
    void removeDiaryPages() {
        //given
        Member member = createMember();
        Auth auth = createAuth(member, "0123456789");
        Diary diary = createDiary(member, 2);
        DiaryPage diaryPage1 = createDiaryPage(diary);
        DiaryPage diaryPage2 = createDiaryPage(diary);
        List<Long> diaryPageIds = List.of(diaryPage1.getId(), diaryPage2.getId());

        //when
        DiaryPageRemoveResponse response = diaryPageService.removeDiaryPages(diary.getId(), diaryPageIds);

        //then
        assertThat(response)
            .isNotNull()
            .hasFieldOrPropertyWithValue("removedPageCount", 2);

        List<DiaryPage> findDiaryPages = diaryPageRepository.findAllByIdIn(diaryPageIds);
        assertThat(findDiaryPages).hasSize(2)
            .extracting("id", "isDeleted")
            .containsExactlyInAnyOrder(
                tuple(diaryPage1.getId(), true),
                tuple(diaryPage2.getId(), true)
            );

        Optional<Diary> findDiary = diaryRepository.findById(diary.getId());
        assertThat(findDiary).isPresent();
        assertThat(findDiary.get().getPageCount()).isZero();
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
            .expiredDateTime(null)
            .member(member)
            .build();
        return authRepository.save(auth);
    }

    private Diary createDiary(Member member, int pageCount) {
        Diary diary = Diary.builder()
            .isDeleted(false)
            .isMain(true)
            .title("후이바오")
            .loveInfo(LoveInfo.builder()
                .isLove(false)
                .startedDate(null)
                .finishedDate(null)
                .build())
            .pageCount(pageCount)
            .profile(null)
            .isStored(false)
            .member(member)
            .build();
        return diaryRepository.save(diary);
    }

    private DiaryPage createDiaryPage(Diary diary) {
        DiaryPage diaryPage = DiaryPage.builder()
            .isDeleted(false)
            .title("장난꾸러기 후이바오")
            .content("우리의 후쪽이")
            .recordDate(LocalDate.of(2024, 3, 20))
            .analysis(AnalysisResult.builder()
                .analysisStatus(AnalysisStatus.BEFORE)
                .build())
            .diary(diary)
            .build();
        return diaryPageRepository.save(diaryPage);
    }
}