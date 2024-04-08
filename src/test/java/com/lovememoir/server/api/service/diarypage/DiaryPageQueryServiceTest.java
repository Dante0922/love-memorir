package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.api.SliceResponse;
import com.lovememoir.server.api.service.diarypage.response.DiaryPageResponse;
import com.lovememoir.server.domain.attachedimage.AttachedImage;
import com.lovememoir.server.domain.attachedimage.repository.AttachedImageRepository;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.LoveInfo;
import com.lovememoir.server.domain.diary.UploadFile;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diarypage.AnalysisResult;
import com.lovememoir.server.domain.diarypage.AnalysisStatus;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class DiaryPageQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private DiaryPageQueryService diaryPageQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private DiaryPageRepository diaryPageRepository;

    @Autowired
    private AttachedImageRepository attachedImageRepository;

    @DisplayName("일기장에 등록된 일기가 없는 경우 빈 리스트를 반환한다.")
    @Test
    void searchDiaryPagesIsEmpty() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);

        PageRequest pageRequest = PageRequest.of(0, 3);

        //when
        SliceResponse<DiaryPagesResponse> response = diaryPageQueryService.searchDiaryPages(diary.getId(), pageRequest);

        //then
        assertThat(response)
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 3)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", true);

        assertThat(response.getContent()).isEmpty();
    }

    @DisplayName("일기장 식별키로 일기 목록을 조회할 수 있다.")
    @Test
    void searchDiaryPages() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);
        DiaryPage diaryPage1 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 20));
        DiaryPage diaryPage2 = createDiaryPage(diary, true, LocalDate.of(2024, 3, 20));
        DiaryPage diaryPage3 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 22));
        DiaryPage diaryPage4 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 21));
        DiaryPage diaryPage5 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 21));
        DiaryPage diaryPage6 = createDiaryPage(diary, false, LocalDate.of(2024, 3, 20));
        PageRequest pageRequest = PageRequest.of(0, 3);

        //when
        SliceResponse<DiaryPagesResponse> response = diaryPageQueryService.searchDiaryPages(diary.getId(), pageRequest);

        //then
        assertThat(response)
            .hasFieldOrPropertyWithValue("currentPage", 1)
            .hasFieldOrPropertyWithValue("size", 3)
            .hasFieldOrPropertyWithValue("isFirst", true)
            .hasFieldOrPropertyWithValue("isLast", false);

        assertThat(response.getContent()).hasSize(3)
            .extracting("diaryPageId")
            .containsExactly(diaryPage3.getId(), diaryPage5.getId(), diaryPage4.getId());
    }

    @DisplayName("일기 식별키로 일기를 상세 조회한다.")
    @Test
    void searchDiaryPage() {
        //given
        Member member = createMember();
        Diary diary = createDiary(member);
        DiaryPage diaryPage = createDiaryPage(diary, false, LocalDate.of(2024, 3, 20));
        AttachedImage attachedImage1 = createAttachedImage(diaryPage, "image1.jpg", false);
        AttachedImage attachedImage2 = createAttachedImage(diaryPage, "image2.jpg", true);
        AttachedImage attachedImage3 = createAttachedImage(diaryPage, "image3.jpg", false);

        //when
        DiaryPageResponse response = diaryPageQueryService.searchDiaryPage(diaryPage.getId());

        //then
        assertThat(response)
            .hasFieldOrPropertyWithValue("diaryPageId", diaryPage.getId())
            .hasFieldOrPropertyWithValue("analysisStatus", AnalysisStatus.BEFORE)
            .hasFieldOrPropertyWithValue("emotionCode", null)
            .hasFieldOrPropertyWithValue("title", "장난꾸러기 후이바오")
            .hasFieldOrPropertyWithValue("content", "우리의 후쪽이")
            .hasFieldOrPropertyWithValue("recordDate", LocalDate.of(2024, 3, 20))
            .hasFieldOrPropertyWithValue("createdDateTime", diaryPage.getCreatedDateTime());

        assertThat(response.getImages()).hasSize(2)
            .extracting("imageId", "url")
            .containsExactly(
                tuple(attachedImage1.getId(), "image1.jpg"),
                tuple(attachedImage3.getId(), "image3.jpg")
            );
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

    private Diary createDiary(Member member) {
        Diary diary = Diary.builder()
            .isDeleted(false)
            .isMain(true)
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

    private DiaryPage createDiaryPage(Diary diary, boolean isDeleted, LocalDate recordDate) {
        DiaryPage diaryPage = DiaryPage.builder()
            .isDeleted(isDeleted)
            .title("장난꾸러기 후이바오")
            .content("우리의 후쪽이")
            .recordDate(recordDate)
            .analysis(AnalysisResult.builder()
                .analysisStatus(AnalysisStatus.BEFORE)
                .build())
            .diary(diary)
            .build();
        return diaryPageRepository.save(diaryPage);
    }

    private AttachedImage createAttachedImage(DiaryPage diaryPage, String storeFileUrl, boolean isDeleted) {
        AttachedImage attachedImage = AttachedImage.builder()
            .isDeleted(isDeleted)
            .image(UploadFile.builder()
                .uploadFileName("upload-file-name.jpg")
                .storeFileUrl(storeFileUrl)
                .build())
            .diaryPage(diaryPage)
            .build();
        return attachedImageRepository.save(attachedImage);
    }
}