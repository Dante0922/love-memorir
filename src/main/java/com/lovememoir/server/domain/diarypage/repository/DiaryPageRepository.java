package com.lovememoir.server.domain.diarypage.repository;

import com.lovememoir.server.domain.diarypage.DiaryPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryPageRepository extends JpaRepository<DiaryPage, Long> {
}
