package com.lovememoir.server.domain.diary;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isMain;

    @Column(nullable = false, length = 8)
    private String title;

    @Embedded
    private LoveInfo loveInfo;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int pageCount;

    @Embedded
    private UploadFile profile;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isStored;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private Diary(boolean isDeleted, boolean isMain, String title, LoveInfo loveInfo, int pageCount, UploadFile profile, boolean isStored, Member member) {
        super(isDeleted);
        this.isMain = isMain;
        this.title = title;
        this.loveInfo = loveInfo;
        this.pageCount = pageCount;
        this.profile = profile;
        this.isStored = isStored;
        this.member = member;
    }

    public static Diary create(String title, LoveInfo loveInfo, Member member, boolean isMain) {
        return Diary.builder()
            .isDeleted(false)
            .isMain(isMain)
            .title(title)
            .loveInfo(loveInfo)
            .pageCount(0)
            .profile(null)
            .isStored(false)
            .member(member)
            .build();
    }

    public void modify(String title, LoveInfo loveInfo) {
        this.title = title;
        this.loveInfo = loveInfo;
    }

    public void modifyProfile(UploadFile profile) {
        this.profile = profile;
    }

    public void modifyStoreStatus() {
        isStored = !isStored;
    }

    public void modifyMainStatus(boolean isMain) {
        this.isMain = isMain;
    }

    public void pageCountUp() {
        pageCount += 1;
    }

    public void pageCountDown(int downCount) {
        int result = pageCount - downCount;
        if (result < 0) {
            throw new IllegalArgumentException();
        }
        pageCount = result;
    }

    public boolean isNotMine(Member member) {
        return !this.member.getId().equals(member.getId());
    }
}
