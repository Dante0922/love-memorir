package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.api.SliceResponse;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageQueryRepository;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPageResponse;
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

    public SliceResponse<DiaryPagesResponse> searchDiaryPages(Long diaryId, Pageable pageable) {
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

    public DiaryPageResponse searchDiaryPage(Long diaryPageId) {
        return diaryPageQueryRepository.findById(diaryPageId)
            .orElseThrow(() -> new NoSuchElementException(NO_SUCH_DIARY_PAGE));
    }
}
