package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.api.controller.diarypage.response.DiaryPageCreateResponse;
import com.lovememoir.server.api.service.diarypage.request.DiaryPageCreateServiceRequest;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diarypage.repository.DiaryPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class DiaryPageService {

    private final DiaryPageRepository diaryPageRepository;
    private final DiaryRepository diaryRepository;

    public DiaryPageCreateResponse createDiaryPage(String memberKey, Long diaryId, DiaryPageCreateServiceRequest request) {
        return null;
    }
}
