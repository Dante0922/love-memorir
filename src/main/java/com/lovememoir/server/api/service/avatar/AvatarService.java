package com.lovememoir.server.api.service.avatar;


import com.lovememoir.server.api.controller.avatar.response.AvatarRefreshResponse;
import com.lovememoir.server.domain.avatar.repository.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class AvatarService {

    private final AvatarRepository avatarRepository;

    public AvatarRefreshResponse refreshAvatar() {
        return AvatarRefreshResponse.builder()
                .emotion("H2")
                .question("오늘은 무슨 일이 있었나요?")
                .build();
    }
}
