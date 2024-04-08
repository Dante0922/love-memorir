package com.lovememoir.server.domain.attachedimage.repository;

import com.lovememoir.server.domain.attachedimage.AttachedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachedImageRepository extends JpaRepository<AttachedImage, Long> {
}
