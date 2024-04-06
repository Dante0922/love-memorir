package com.lovememoir.server.domain.diarypage;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.diary.Diary;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.lovememoir.server.domain.diarypage.AnalysisResult.init;
import static com.lovememoir.server.domain.diarypage.AnalysisResult.success;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryPage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_page_id")
    private Long id;

    @Column(nullable = false, length = 10)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false)
    private LocalDate diaryDate;

    @Embedded
    private AnalysisResult analysisResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    private DiaryPage(boolean isDeleted, String title, String content, LocalDate diaryDate, AnalysisResult analysisResult, Diary diary) {
        super(isDeleted);
        this.title = title;
        this.content = content;
        this.diaryDate = diaryDate;
        this.analysisResult = analysisResult;
        this.diary = diary;
    }
}
