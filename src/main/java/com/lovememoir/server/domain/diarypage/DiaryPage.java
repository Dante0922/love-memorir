package com.lovememoir.server.domain.diarypage;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.diary.Diary;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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

    //TODO: 2024-03-26 11:50 dong82 일기 감정 캐릭터 추가 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @Builder
    private DiaryPage(boolean isDeleted, String title, String content, LocalDate diaryDate, Diary diary) {
        super(isDeleted);
        this.title = title;
        this.content = content;
        this.diaryDate = diaryDate;
        this.diary = diary;
    }

    public static DiaryPage create(String title, String content, LocalDate diaryDate, Diary diary) {
        diary.increasePageCount();

        return DiaryPage.builder()
            .title(title)
            .content(content)
            .diaryDate(diaryDate)
            .diary(diary)
            .build();
    }
}
