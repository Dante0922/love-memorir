package com.lovememoir.server.domain.diary.repository;

import com.lovememoir.server.domain.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("select d from Diary d where d.member.id = :memberId and d.isDeleted = false and d.isMain = true")
    Optional<Diary> findMainDiaryByMemberId(Long memberId);
}
