package com.lovememoir.server.api;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

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
