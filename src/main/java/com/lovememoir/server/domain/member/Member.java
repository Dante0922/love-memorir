package com.lovememoir.server.domain.member;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.auth.Auth;
import com.lovememoir.server.domain.avatar.Avatar;
import com.lovememoir.server.domain.member.enumerate.Gender;
import com.lovememoir.server.domain.member.enumerate.RoleType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    //TODO nullable = false
    @Column(unique = true, length = 36, columnDefinition = "char(36)")
    private String memberKey;

    @Column
    private String email;

    @Column(nullable = false, length = 8)
    private String nickname;

    @Column(nullable = false, updatable = false, length = 1, columnDefinition = "char(1)")
    private Gender gender;

    @Column(nullable = false, length = 10, columnDefinition = "char(10)")
    private String birth;

    @ElementCollection
    @Column(nullable = false, length = 5)
    private Set<RoleType> roleType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Auth> authSet;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    //TODO diary, term 속성 추가
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "diary")
//    private List<Diary> diaries;

    @Builder
    private Member(String memberKey, String nickname, String email, Gender gender, String birth, Set<RoleType> roleType) {
        this.memberKey = memberKey;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.roleType = roleType;
    }

    public static Member create(String nickname, String email, Gender gender, String birth) {
        return Member.builder()
            .memberKey(UUID.randomUUID().toString())
            .nickname(nickname)
            .email(email)
            .gender(gender)
            .birth(birth)
            .build();
    }
}

