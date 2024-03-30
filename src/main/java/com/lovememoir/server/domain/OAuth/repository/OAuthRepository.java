package com.lovememoir.server.domain.OAuth.repository;

import com.lovememoir.server.domain.OAuth.OAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthRepository extends JpaRepository<OAuth, Long>{

}
