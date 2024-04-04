package com.lovememoir.server.domain.auth.repository;

import com.lovememoir.server.domain.auth.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth, String>{

}
