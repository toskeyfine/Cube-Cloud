package com.toskey.cube.common.security.component;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * CubeAccessDeniedHandler
 *
 * @author toskey
 * @version 1.0.0
 */
public class RequestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String resultMsg = "权限不足";
        if (accessDeniedException != null) {
            resultMsg = accessDeniedException.getMessage();
        }
        ResponseUtils.writeJson(RestResult.of(HttpStatus.UNAUTHORIZED.value(), resultMsg), response);
    }
}
