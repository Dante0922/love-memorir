package com.lovememoir.server.domain.diaryanalysis.repository;

import com.lovememoir.server.domain.diaryanalysis.DiaryAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryAnalysisRepository extends JpaRepository<DiaryAnalysis, Long> {

}
