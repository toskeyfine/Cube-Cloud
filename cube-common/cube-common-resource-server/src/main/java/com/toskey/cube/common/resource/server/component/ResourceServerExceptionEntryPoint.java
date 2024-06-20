package com.toskey.cube.common.resource.server.component;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.util.ResponseUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kotlin.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * ResourceServerExceptionEntryPoint
 *
 * @author toskey
 * @version 1.0.0
 */
public class ResourceServerExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        RestResult<Void> result = RestResult.of(HttpStatus.UNAUTHORIZED.value());
        if (exception != null) {
            String errMsg = exception.getMessage();
            if (exception instanceof OAuth2AuthenticationException authenticationException) {
                errMsg = authenticationException.getError().getDescription();
            } else if (exception instanceof InsufficientAuthenticationException) {
                errMsg = "您没有该资源的访问权限";
            }
            result.setMsg(errMsg);
        }
        ResponseUtils.writeJson(result, response);
    }
}
