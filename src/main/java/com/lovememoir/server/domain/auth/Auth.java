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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToOne(mappedBy = "auth", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Member member;

    @Column(nullable = false)
    private ProviderType provider;

    @Column(nullable = false)
    private String providerId;

    private String accessToken;

    private String refreshToken;

    private LocalDateTime expiredAt;

    @Builder
    private Auth(Member member, ProviderType provider, String providerId, String accessToken, String refreshToken, LocalDateTime expiredAt) {
        this.member = member;
        this.provider = provider;
        this.providerId = providerId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }

    public static Auth create(Member member, ProviderType provider, String providerId, String accessToken, String refreshToken, LocalDateTime expiredAt) {
        return Auth.builder()
                .member(member)
                .provider(provider)
                .providerId(providerId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiredAt(expiredAt)
                .build();
    }
}
