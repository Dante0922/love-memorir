package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.api.FileStore;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageCreateResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageModifyResponse;
import com.lovememoir.server.api.controller.diarypage.response.DiaryPageRemoveResponse;
import com.lovememoir.server.api.service.diaryanalysis.DiaryAnalysisService;
import com.lovememoir.server.api.service.diarypage.request.DiaryPageCreateServiceRequest;
import com.lovememoir.server.api.service.diarypage.request.DiaryPageModifyServiceRequest;
import com.lovememoir.server.common.exception.AuthException;
import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.attachedimage.AttachedImage;
import com.lovememoir.server.domain.attachedimage.repository.AttachedImageRepository;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.UploadFile;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static com.lovememoir.server.api.service.diarypage.DiaryPageValidator.*;
import static com.lovememoir.server.common.message.ExceptionMessage.*;


@RequiredArgsConstructor
@Service
@Transactional
public class DiaryPageService {

    private final DiaryPageRepository diaryPageRepository;
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final AttachedImageRepository attachedImageRepository;
    private final FileStore fileStore;
    private final DiaryAnalysisService diaryAnalysisService;

    public DiaryPageCreateResponse createDiaryPage(final String providerId, final Long diaryId, final LocalDate currentDate, DiaryPageCreateServiceRequest request) {
        String title = validateTitle(request.getTitle());
        LocalDate recordDate = validateRecordDate(request.getRecordDate(), currentDate);
        List<MultipartFile> files = validateImageCount(request.getImages());

        Member member = memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));

        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY));

        if (diary.isNotMine(member)) {
            throw new AuthException(NO_AUTH);
        }

        List<UploadFile> images = cloudUploadFiles(files);
        DiaryPage diaryPage = DiaryPage.create(title, request.getContent(), recordDate, diary);
        DiaryPage savedDiaryPage = diaryPageRepository.save(diaryPage);

        List<AttachedImage> attachedImages = AttachedImage.create(images, savedDiaryPage);
        List<AttachedImage> savedAttachedImages = attachedImageRepository.saveAll(attachedImages);

        diary.pageCountUp();

        try {
            diaryAnalysisService.diaryAnalysis(savedDiaryPage.getId());
        } catch (ParseException e) {
            diaryPage.failAnalysis();
        }

        return DiaryPageCreateResponse.of(savedDiaryPage, savedAttachedImages);
    }

    //todo: 2024-04-07 dong82 이미지 수정 여부
    public DiaryPageModifyResponse modifyDiaryPage(final String providerId, final long diaryPageId, final LocalDate currentDate, DiaryPageModifyServiceRequest request) {
        String title = validateTitle(request.getTitle());
        LocalDate recordDate = validateRecordDate(request.getRecordDate(), currentDate);

        Member member = memberRepository.findByProviderId(providerId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_MEMBER));

        DiaryPage diaryPage = diaryPageRepository.findWithDiaryById(diaryPageId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY_PAGE));

        if (diaryPage.getDiary().isNotMine(member)) {
            throw new AuthException(NO_AUTH);
        }

        diaryPage.modify(title, request.getContent(), recordDate);

        return DiaryPageModifyResponse.of(diaryPage);
    }

    public DiaryPageRemoveResponse removeDiaryPages(final long diaryId, final List<Long> diaryPageIds) {
        Diary diary = diaryRepository.findById(diaryId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY));

        List<DiaryPage> diaryPages = diaryPageRepository.findAllByIdIn(diaryPageIds);

        diaryPages.forEach(BaseTimeEntity::remove);

        diary.pageCountDown(diaryPages.size());

        return DiaryPageRemoveResponse.of(diaryPages.size());
    }

    private List<UploadFile> cloudUploadFiles(List<MultipartFile> files) {
        try {
            return fileStore.storeFiles(files);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
