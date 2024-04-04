package com.lovememoir.server.api;

import lombok.Getter;

import java.util.List;

/**
 * List Response 공통 스팩
 *
 * @param <T> 데이터 타입
 * @author dong82
 */
@Getter
public class ListResponse<T> {

    private final int size;
    private final List<T> content;

    private ListResponse(List<T> data) {
        this.size = data.size();
        this.content = data;
    }

    public static <T> ListResponse<T> of(List<T> data) {
        return new ListResponse<>(data);
    }
}
