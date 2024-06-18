package com.toskey.cube.cloud.handler;

import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.util.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


/**
 * FormAuthenticationSuccessHandler
 *
 * @author toskey
 * @version 1.0.0
 */
public class FormAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        ResponseUtils.writeJson(RestResult.success(), response);
    }

}
