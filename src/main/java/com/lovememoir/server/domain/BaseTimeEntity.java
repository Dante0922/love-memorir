package com.lovememoir.server.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

/**
 * 생성일, 최종 수정일 공통 관리 클래스
 *
 * @author 임우택
 */
@MappedSuperclass
@Getter
@NoArgsConstructor(access = PROTECTED)
public class BaseTimeEntity {

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isDeleted;

    @CreatedDate
    @Column(nullable = false, updatable = false, columnDefinition = "datetime default current_timestamp")
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "datetime default current_timestamp on update current_timestamp")
    private LocalDateTime lastModifiedDateTime;

    protected BaseTimeEntity(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void remove() {
        this.isDeleted = true;
    }
}