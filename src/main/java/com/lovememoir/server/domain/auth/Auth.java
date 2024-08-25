package com.lovememoir.server.domain.auth;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    public Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 20, columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    @Column(nullable = false)
    private String providerId;

    private String accessToken;

    private String refreshToken;

    private LocalDateTime expiredDateTime;

    @Builder
    private Auth(Member member, ProviderType provider, String providerId, String accessToken, String refreshToken, LocalDateTime expiredDateTime) {
        this.member = member;
        this.provider = provider;
        this.providerId = providerId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiredDateTime = expiredDateTime;
    }

    public static Auth create(Member member, ProviderType provider, String providerId, String accessToken, String refreshToken, LocalDateTime expiredDateTime) {
        return Auth.builder()
            .member(member)
            .provider(provider)
            .providerId(providerId)
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .expiredDateTime(expiredDateTime)
            .build();
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
