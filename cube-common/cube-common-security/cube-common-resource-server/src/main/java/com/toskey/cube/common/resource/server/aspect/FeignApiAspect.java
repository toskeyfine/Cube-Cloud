package com.toskey.cube.common.resource.server.aspect;

import com.toskey.cube.common.core.constant.SecurityConstants;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.resource.server.annotation.FeignApi;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.springframework.security.access.AccessDeniedException;

/**
 * FeignApiAspect
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:17
 */
public class FeignApiAspect {

    private final HttpServletRequest request;

    public FeignApiAspect(final HttpServletRequest request) {
        this.request = request;
    }

    @SneakyThrows
    @Around("@annotation(feignApi)")
    public Object around(ProceedingJoinPoint point, FeignApi feignApi) {
        String header = request.getHeader(SecurityConstants.HEADER_SERVICE);
        if (!StringUtils.equals(SecurityConstants.HEADER_SERVICE_CUBE, header)) {
            throw new AccessDeniedException("Access is denied.");
        }
        return point.proceed();
    }

}
