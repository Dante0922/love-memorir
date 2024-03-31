package com.lovememoir.server.common.exception;

import lombok.Builder;

import java.util.Map;

public record ErrorResponse(String code, String message, Map<String, String> validation) {
    @Builder
    public ErrorResponse {
    }

    public void addValidation(String field, String message) {
        this.validation.put(field, message);
    }
}