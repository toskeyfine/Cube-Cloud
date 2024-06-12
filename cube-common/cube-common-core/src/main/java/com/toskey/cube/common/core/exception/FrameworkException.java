package com.toskey.cube.common.core.exception;

import lombok.Getter;

/**
 * FrameworkException
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/12 15:51
 */
public class FrameworkException extends RuntimeException {

    @Getter
    private String code;

    private String message;

    public FrameworkException(String message) {
        super(message);
        this.code = "200";
        this.message = message;
    }

    public FrameworkException(String code, String message) {
        this(message);
        this.code = code;
    }

    public FrameworkException(String message, Throwable t) {
        super(message, t);
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
