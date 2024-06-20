package com.toskey.cube.common.sentinel.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.toskey.cube.common.core.base.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionAdvice
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/19 10:46
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RestResult<Void> handleAccessDeniedException(AccessDeniedException e) {
        return RestResult.failure("权限不足，不允许访问");
    }

}
