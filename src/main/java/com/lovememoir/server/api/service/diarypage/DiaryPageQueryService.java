package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.api.SliceResponse;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageQueryRepository;
import com.lovememoir.server.domain.diarypage.repository.response.DiaryPagesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DiaryPageQueryService {

    private final DiaryPageQueryRepository diaryPageQueryRepository;

    public SliceResponse<DiaryPagesResponse> searchDiaryPages(Long diaryId, Pageable pageable) {

        return null;
    }
}
