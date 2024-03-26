package com.lovememoir.server.domain.diary;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isFixed;

    @Column(nullable = false, length = 15)
    private String title;

    private LocalDate relationshipStartedDate;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int pageCount;

    @Embedded
    private UploadFile file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Diary(boolean isDeleted, boolean isFixed, String title, LocalDate relationshipStartedDate, int pageCount, UploadFile file, Member member) {
        super(isDeleted);
        this.isFixed = isFixed;
        this.title = title;
        this.relationshipStartedDate = relationshipStartedDate;
        this.pageCount = pageCount;
        this.file = file;
        this.member = member;
    }

    public static Diary create(String title, LocalDate relationshipStartedDate, Member member) {
        return Diary.builder()
            .isFixed(false)
            .title(title)
            .relationshipStartedDate(relationshipStartedDate)
            .pageCount(0)
            .member(member)
            .build();
    }

    public void modify(String title, LocalDate relationshipStartedDate) {
        this.title = title;
        this.relationshipStartedDate = relationshipStartedDate;
    }

    public void modifyFile(UploadFile file) {
        this.file = file;
    }

    public boolean isMine(Member member) {
        return this.member.getId().equals(member.getId());
    }

    public void increasePageCount() {
        pageCount += 1;
    }
}
