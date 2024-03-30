package com.lovememoir.server.auth.client;

import com.lovememoir.server.domain.OAuth.OAuth;

public interface ClientProxy {

    OAuth getOAuth(String accessToken);
}
