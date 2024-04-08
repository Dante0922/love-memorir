package com.lovememoir.server.domain.attachedimage.repository;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.attachedimage.AttachedImage;
import com.lovememoir.server.domain.attachedimage.repository.response.AttachedImageResponse;
import com.lovememoir.server.domain.diary.UploadFile;
import com.lovememoir.server.domain.diarypage.AnalysisResult;
import com.lovememoir.server.domain.diarypage.AnalysisStatus;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class AttachedImageQueryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private AttachedImageQueryRepository attachedImageQueryRepository;

    @Autowired
    private DiaryPageRepository diaryPageRepository;

    @Autowired
    private AttachedImageRepository attachedImageRepository;

    @DisplayName("일기 식별키로 첨부 이미지 경로를 조회한다.")
    @Test
    void findAllByDiaryPageId() {
        //given
        DiaryPage diaryPage = createDiaryPage();
        AttachedImage attachedImage1 = createAttachedImage(diaryPage, "image1.jpg", false);
        AttachedImage attachedImage2 = createAttachedImage(diaryPage, "image2.jpg", true);
        AttachedImage attachedImage3 = createAttachedImage(diaryPage, "image3.jpg", false);

        //when
        List<AttachedImageResponse> content = attachedImageQueryRepository.findAllByDiaryPageId(diaryPage.getId());

        //then
        assertThat(content).hasSize(2)
            .extracting("imageId", "url")
            .containsExactly(
                tuple(attachedImage1.getId(), "image1.jpg"),
                tuple(attachedImage3.getId(), "image3.jpg")
            );
    }

    private DiaryPage createDiaryPage() {
        DiaryPage diaryPage = DiaryPage.builder()
            .isDeleted(false)
            .title("장난꾸러기 후이바오")
            .content("우리의 후쪽이")
            .recordDate(LocalDate.of(2024, 4, 1))
            .analysis(AnalysisResult.builder()
                .analysisStatus(AnalysisStatus.BEFORE)
                .build())
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