package com.toskey.cube.common.resource.server.component;

import com.toskey.cube.common.core.base.RestResult;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

/**
 * CubeAccessDeniedHandler
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 10:16
 */
public class CubeAccessDeniedHandler implements AccessDeniedHandler {

    private final MappingJackson2HttpMessageConverter httpMessageConverter = new MappingJackson2HttpMessageConverter();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String resultMsg = "权限不足";
        if (accessDeniedException != null) {
            resultMsg = accessDeniedException.getMessage();
        }
        this.httpMessageConverter.write(
                RestResult.of(HttpStatus.UNAUTHORIZED.value(), resultMsg),
                MediaType.valueOf("application/json"),
                new ServletServerHttpResponse(response)
        );
    }
}
