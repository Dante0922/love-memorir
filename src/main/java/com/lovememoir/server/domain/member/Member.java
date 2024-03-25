package com.lovememoir.server.domain.member;

import com.lovememoir.server.domain.avatar.Avatar;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 36)
    private String memberKey;

    @Column(nullable = false, length = 8)
    private String nickname;

    @Column(nullable = false, length = 1)
    private String gender; // TODO Enum 으로 변경?

    @Column(nullable = false, length = 8)
    private String birth;

    @Column(nullable = false, length = 5)
    private String role;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Avatar avatar;

    //TODO diary, term 속성 추가
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "diary")
//    private List<Diary> diaries;

    @Builder
    public Member(String memberKey, String nickname, String gender, String birth, String role) {
        this.memberKey = memberKey;
        this.nickname = nickname;
        this.gender = gender;
        this.birth = birth;
        this.role = role;
    }
}

