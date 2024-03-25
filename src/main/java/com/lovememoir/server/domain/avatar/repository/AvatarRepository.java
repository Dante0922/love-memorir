package com.lovememoir.server.domain.avatar.repository;

import com.lovememoir.server.domain.avatar.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvatarRepository extends JpaRepository<Avatar, Long>{
}
