package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.domain.diary.repository.DiaryQueryRepository;
import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DiaryQueryService {

    private final DiaryQueryRepository diaryQueryRepository;

    public List<DiarySearchResponse> searchDiaries(final String memberKey) {
        return null;
    }

    public List<DiarySearchResponse> searchMainDiaries(final String memberKey) {
        return null;
    }
}
