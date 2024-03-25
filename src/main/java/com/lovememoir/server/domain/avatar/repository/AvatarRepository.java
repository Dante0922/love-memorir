package com.lovememoir.server.domain.avatar.repository;

import com.lovememoir.server.domain.avatar.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long>{
}
