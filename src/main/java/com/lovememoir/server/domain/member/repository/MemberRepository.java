package com.lovememoir.server.domain.member.repository;

import com.lovememoir.server.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
