package com.lovememoir.server.domain.attachedimage.repository;

import com.lovememoir.server.domain.attachedimage.QAttachedImage;
import com.lovememoir.server.domain.attachedimage.repository.response.AttachedImageResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.lovememoir.server.domain.attachedimage.QAttachedImage.*;

@Repository
public class AttachedImageQueryRepository {

    private final JPQLQueryFactory queryFactory;

    public AttachedImageQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<AttachedImageResponse> findAllByDiaryPageId(final long diaryPageId) {
        return queryFactory
            .select(
                Projections.fields(
                    AttachedImageResponse.class,
                    attachedImage.id.as("imageId"),
                    attachedImage.image.storeFileUrl.as("url")
                )
            )
            .from(attachedImage)
            .where(
                attachedImage.isDeleted.isFalse(),
                attachedImage.diaryPage.id.eq(diaryPageId)
            )
            .orderBy(attachedImage.createdDateTime.asc())
            .fetch();
    }
}
