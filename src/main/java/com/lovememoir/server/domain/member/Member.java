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

import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberKey;

    @Column
    private String email;

    @Column(nullable = false, length = 8)
    private String nickname;

    @Column(nullable = false, length = 1, columnDefinition = "char(1)")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false, length = 10, columnDefinition = "char(10)")
    private String birth;

    @Column(nullable = false, length = 20, columnDefinition = "varchar(20)")
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Auth auth;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Avatar avatar;

    //TODO diary, term 속성 추가
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "diary")
//    private List<Diary> diaries;

    @Builder
    private Member(String nickname, String memberKey, String email, Gender gender, String birth, RoleType roleType, Auth auth) {
        this.nickname = nickname;
        this.memberKey = UUID.randomUUID().toString();
        this.email = email;
        this.gender = gender;
        this.birth = birth;
        this.roleType = roleType;
        this.auth = auth;
        if (auth != null) {
            auth.setMember(this);
        }
    }

    public static Member create(String nickname, Gender gender, String birth, RoleType roleType, Auth auth) {
        return Member.builder()
            .nickname(nickname)
            .gender(gender)
            .birth(birth)
            .roleType(roleType)
            .auth(auth)
            .build();
    }

    public void modify(String nickname, String birth, Gender gender) {
        this.nickname = nickname;
        this.birth = birth;
        this.gender = gender;
    }
}

