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
public class SystemCodeGroup extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_code")
    private Integer code;

    @Column(nullable = false, length = 10)
    private String name;

    @Builder
    private SystemCodeGroup(boolean isDeleted, String name) {
        super(isDeleted);
        this.name = name;
    }
}
