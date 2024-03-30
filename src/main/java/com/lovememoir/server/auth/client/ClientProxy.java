package com.lovememoir.server.auth.client;

import com.lovememoir.server.domain.member.Member;

public interface ClientProxy {

    Member getUserData(String accessToken);
}
