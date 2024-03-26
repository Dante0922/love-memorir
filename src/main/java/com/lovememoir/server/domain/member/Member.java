package com.lovememoir.server.domain.member;

import com.lovememoir.server.domain.BaseTimeEntity;
import com.lovememoir.server.domain.avatar.Avatar;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false, length = 36, columnDefinition = "char(36)")
    private String memberKey;

    @Column(nullable = false, length = 8)
    private String nickname;

    @Column(nullable = false, updatable = false, length = 1, columnDefinition = "char(1)")
    private Gender gender;

    @Column(nullable = false, length = 10, columnDefinition = "char(10)")
    private String birth;

    @Column(nullable = false, length = 5)
    private Role role;

    @OneToOne
    @JoinColumn(name = "avatar_id")
    private Avatar avatar;

    //TODO diary, term 속성 추가
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "diary")
//    private List<Diary> diaries;

    @Builder
    private Member(String memberKey, String nickname, Gender gender, String birth, Role role) {
        this.memberKey = memberKey;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.role = role;
    }

    public static Member create(String nickname, Gender gender, String birth) {
        return Member.builder()
            .memberKey(UUID.randomUUID().toString())
            .nickname(nickname)
            .gender(gender)
            .birth(birth)
            .role(Role.USER)
            .build();
    }
}

