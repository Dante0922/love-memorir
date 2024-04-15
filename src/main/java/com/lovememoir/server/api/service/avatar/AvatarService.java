package com.lovememoir.server.api.service.avatar;


import com.lovememoir.server.api.controller.avatar.response.AvatarCreateResponse;
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

    public AvatarCreateResponse createAvatar() {

        //logic..
        return null;

    }

    public AvatarRefreshResponse refreshAvatar() {
        return AvatarRefreshResponse.builder()
                .emotion("H2")
                .question("오늘은 무슨 일이 있었나요?")
                .build();
    }

    private String generateQuestion() {

        //TODO 일기분석 토대로 질문 생성
        // 질문은 1일 1질문으로 제공..
        // 질문은 어디에 저장할지, 어떻게 제공여부 파악할지..(redis?)


        return "오늘은 무슨 일이 있었나요?";
    }
}
