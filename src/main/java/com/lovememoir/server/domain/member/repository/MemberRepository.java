package com.lovememoir.server.domain.member.repository;

import com.lovememoir.server.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join m.auth a where a.providerId = :providerId")
    Optional<Member> findByProviderId(String providerId);
}
