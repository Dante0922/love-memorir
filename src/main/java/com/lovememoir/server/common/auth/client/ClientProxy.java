package com.lovememoir.server.common.auth.client;

import com.lovememoir.server.domain.auth.Auth;

public interface ClientProxy {

    Auth getOAuth(String accessToken);
}
