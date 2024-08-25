package com.lovememoir.server.domain.diary.repository;

import com.lovememoir.server.domain.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("select d.id from Diary d where d.member.id = :memberId and d.isMain = true and d.isDeleted = false")
    Optional<Long> findMainDiaryByMemberId(@Param("memberId") Long memberId);
}
