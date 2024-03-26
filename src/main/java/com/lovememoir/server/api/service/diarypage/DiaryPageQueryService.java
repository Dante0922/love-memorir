package com.lovememoir.server.api.service.diarypage;

import com.lovememoir.server.domain.diarypage.repository.DiaryPageQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class DiaryPageQueryService {

    private final DiaryPageQueryRepository diaryPageQueryRepository;


}
