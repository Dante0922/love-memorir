package com.lovememoir.server.domain.code.repository;

import com.lovememoir.server.domain.code.SystemCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemCodeRepository extends JpaRepository<SystemCode, Integer> {

    @Query("select c from SystemCode c where c.group.code = :groupCode")
    List<SystemCode> findAllByGroupCode(@Param("groupCode") Integer groupCode);
}
