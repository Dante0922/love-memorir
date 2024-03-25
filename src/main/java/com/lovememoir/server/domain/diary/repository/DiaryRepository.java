package com.lovememoir.server.domain.diary.repository;

import com.lovememoir.server.domain.diary.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Query("select count(d.id) from Diary d where d.isDeleted = false and d.member.id = :memberId")
    int countByMemberId(@Param("memberId") Long memberId);
}
