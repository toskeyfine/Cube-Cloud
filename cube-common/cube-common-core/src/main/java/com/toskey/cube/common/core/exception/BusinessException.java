package com.toskey.cube.common.core.exception;

import lombok.Getter;

/**
 * BusinessException
 *
 * @author toskey
 * @version 1.0
 * @since 2024/6/5
 */
public class BusinessException extends RuntimeException {

    @Getter
    private String code;

    private String message;

    public BusinessException(String message) {
        super(message);
        this.code = "200";
        this.message = message;
    }

    public BusinessException(String code, String message) {
        this(message);
        this.code = code;
    }

    public BusinessException(String message, Throwable t) {
        super(message, t);
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
