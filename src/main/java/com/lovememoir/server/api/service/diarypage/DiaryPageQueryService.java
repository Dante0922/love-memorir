package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.api.SliceResponse;
import com.lovememoir.server.api.service.diarypage.response.DiaryPageCountResponse;
import com.lovememoir.server.api.service.diarypage.response.DiaryPageResponse;
import com.lovememoir.server.domain.attachedimage.repository.AttachedImageQueryRepository;
import com.lovememoir.server.domain.attachedimage.repository.response.AttachedImageResponse;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageQueryRepository;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryAnalysisRseponse;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPageDto;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static com.lovememoir.server.common.message.ExceptionMessage.NO_SUCH_DIARY_PAGE;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DiaryPageQueryService {

    private final DiaryPageQueryRepository diaryPageQueryRepository;
    private final AttachedImageQueryRepository attachedImageQueryRepository;

    public DiaryPageCountResponse countDiaryPage(final long diaryId) {
        int pageCount = diaryPageQueryRepository.countAllByDiaryId(diaryId);

        return DiaryPageCountResponse.builder()
            .pageCount(pageCount)
            .build();
    }

    public SliceResponse<DiaryPagesResponse> searchDiaryPages(long diaryId, Pageable pageable) {
        List<Long> diaryIds = diaryPageQueryRepository.findAllIdByDiaryId(diaryId, pageable);

        if (CollectionUtils.isEmpty(diaryIds)) {
            return SliceResponse.of(new ArrayList<>(), pageable, false);
        }

        boolean hasNext = false;
        if (diaryIds.size() > pageable.getPageSize()) {
            diaryIds.remove(pageable.getPageSize());
            hasNext = true;
        }

        List<DiaryPagesResponse> content = diaryPageQueryRepository.findAllByDiaryIdIn(diaryIds);

        return SliceResponse.of(content, pageable, hasNext);
    }

    public DiaryPageResponse searchDiaryPage(long diaryPageId) {
        DiaryPageDto diaryPage = diaryPageQueryRepository.findById(diaryPageId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY_PAGE));

        List<AttachedImageResponse> images = attachedImageQueryRepository.findAllByDiaryPageId(diaryPageId);

        return DiaryPageResponse.of(diaryPage, images);
    }

    public DiaryAnalysisRseponse searchDiaryPageEmotion(Long diaryPageId) {
        return diaryPageQueryRepository.findEmotionByDiaryId(diaryPageId);

    }
}
