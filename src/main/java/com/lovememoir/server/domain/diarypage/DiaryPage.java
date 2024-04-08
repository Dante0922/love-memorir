package com.lovememoir.server.domain.diarypage;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.diary.Diary;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.lovememoir.server.domain.diarypage.AnalysisResult.*;
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

    @Column(nullable = false, length = 20)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Column(nullable = false, columnDefinition = "date")
    private LocalDate recordDate;

    @Embedded
    private AnalysisResult analysis;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    private DiaryPage(boolean isDeleted, String title, String content, LocalDate recordDate, AnalysisResult analysis, Diary diary) {
        super(isDeleted);
        this.title = title;
        this.content = content;
        this.recordDate = recordDate;
        this.analysis = analysis;
        this.diary = diary;
    }

    public static DiaryPage create(String title, String content, LocalDate recordDate, Diary diary) {
        return DiaryPage.builder()
            .isDeleted(false)
            .title(title)
            .content(content)
            .recordDate(recordDate)
            .analysis(init())
            .diary(diary)
            .build();
    }

    public void modify(String title, String content, LocalDate recordDate) {
        this.title = title;
        this.content = content;
        this.recordDate = recordDate;
    }
}
