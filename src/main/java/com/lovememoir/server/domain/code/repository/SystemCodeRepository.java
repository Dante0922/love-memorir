package com.lovememoir.server.domain.code.repository;

import com.lovememoir.server.domain.code.SystemCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemCodeRepository extends JpaRepository<SystemCode, Integer> {
}
