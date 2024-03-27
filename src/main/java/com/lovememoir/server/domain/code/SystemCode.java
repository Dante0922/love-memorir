package com.lovememoir.server.domain.code;

import com.lovememoir.server.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemCode extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code")
    private Integer code;

    @Column(nullable = false, length = 10)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_code")
    private SystemCodeGroup group;

    @Builder
    private SystemCode(boolean isDeleted, String name, SystemCodeGroup group) {
        super(isDeleted);
        this.name = name;
        this.group = group;
    }
}
