package com.lovememoir.server.domain.attachedimage;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.diary.UploadFile;
import com.lovememoir.server.domain.diarypage.DiaryPage;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachedImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attached_image_id")
    private Long id;

    private UploadFile image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_page_id")
    private DiaryPage diaryPage;

    @Builder
    private AttachedImage(boolean isDeleted, UploadFile image, DiaryPage diaryPage) {
        super(isDeleted);
        this.image = image;
        this.diaryPage = diaryPage;
    }

    public static List<AttachedImage> create(List<UploadFile> images, DiaryPage diaryPage) {
        return images.stream()
            .map(image -> new AttachedImage(false, image, diaryPage))
            .collect(Collectors.toList());
    }
}
