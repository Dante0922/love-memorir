package com.lovememoir.server.domain.code.repository;

import com.lovememoir.server.domain.code.SystemCodeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemCodeGroupRepository extends JpaRepository<SystemCodeGroup, Long> {
}
