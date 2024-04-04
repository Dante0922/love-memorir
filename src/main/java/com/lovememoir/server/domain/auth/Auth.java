package com.lovememoir.server.domain.auth;

import com.lovememoir.server.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Auth {

    @Id
    public String id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private ProviderType provider;

    private String accessToken;

    private String refreshToken;

    private LocalDateTime expiredAt;

    @Builder
    private Auth(String id, Member member, ProviderType provider, String accessToken, String refreshToken, LocalDateTime expiredAt) {
        this.id = id;
        this.member = member;
        this.provider = provider;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }

    public static Auth create(String id, Member member, ProviderType provider, String accessToken, String refreshToken, LocalDateTime expiredAt) {
        return Auth.builder()
                .id(id)
                .member(member)
                .provider(provider)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredAt(expiredAt)
                .build();
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
