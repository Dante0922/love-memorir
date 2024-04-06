package com.lovememoir.server.api.service.diary;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.diary.Diary;
import com.lovememoir.server.domain.diary.repository.DiaryRepository;
import com.lovememoir.server.domain.diary.repository.response.DiarySearchResponse;
import com.lovememoir.server.domain.member.Member;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class DiaryQueryServiceTest extends IntegrationTestSupport {

    @Autowired
    private DiaryQueryService diaryQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DiaryRepository diaryRepository;
}