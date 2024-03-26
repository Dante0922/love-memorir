package com.lovememoir.server.common.interceptor.query;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * 쿼리 개수를 저장하기 위한 request 스코프의 빈
 *
 * @author dong82
 */
@Component
@RequestScope
@Getter
public class ApiQueryCounter {

    private int count;

    public void increaseCount() {
        count++;
    }
}
