package com.lovememoir.server.api.service.avatar;

import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import com.lovememoir.server.domain.avatar.Avatar;
import com.lovememoir.server.domain.avatar.repository.AvatarQueryRepository;
import com.lovememoir.server.domain.avatar.repository.response.AvatarResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AvatarQueryService {

    private final AvatarQueryRepository avatarQueryRepository;
    private final AvatarService avatarService;


    public AvatarResponse searchAvatar(String providerId) {
        Optional<Avatar> avatar = avatarQueryRepository.findByProviderId(providerId);
        if (avatar.isEmpty()) {
            AvatarRefreshResponse avatarRefreshResponse = avatarService.refreshAvatar();
            return avatarRefreshResponse.toAvatarResponse();
        }
        return AvatarResponse.of(avatar.get());
    }
}

