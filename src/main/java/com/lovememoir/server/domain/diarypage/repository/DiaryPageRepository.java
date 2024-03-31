package com.lovememoir.server.domain.diarypage.repository;

import com.lovememoir.server.domain.diarypage.DiaryPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryPageRepository extends JpaRepository<DiaryPage, Long> {

    @Query("select dp from DiaryPage dp join fetch dp.diary d where dp.id = :diaryPageId")
    Optional<DiaryPage> findByIdWithDiary(@Param("diaryPageId") Long diaryPageId);

    List<DiaryPage> findAllByIdIn(List<Long> diaryPageIds);
}
