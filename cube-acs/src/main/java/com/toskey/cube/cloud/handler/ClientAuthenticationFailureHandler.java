package com.toskey.cube.cloud.handler;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.util.ResponseUtils;
import com.toskey.cube.common.core.util.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ClientAuthenticationFailureHandler
 *
 * @author toskey
 * @version 1.0.0
 */
@Component
public class ClientAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String errorMessage;

        if (exception instanceof OAuth2AuthenticationException authorizationException) {
            errorMessage = StringUtils.isBlank(authorizationException.getError().getDescription())
                    ? authorizationException.getError().getErrorCode()
                    : authorizationException.getError().getDescription();
        }
        else {
            errorMessage = exception.getLocalizedMessage();
        }
        ResponseUtils.writeJson(RestResult.failure(errorMessage), response);
    }

}
