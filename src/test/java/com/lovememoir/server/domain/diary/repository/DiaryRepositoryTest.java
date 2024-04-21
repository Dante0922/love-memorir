package com.lovememoir.server.domain.diary.repository;

import com.lovememoir.server.IntegrationTestSupport;
import com.lovememoir.server.domain.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

class DiaryRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private DiaryRepository diaryRepository;

    @Autowired
    private MemberRepository memberRepository;

}