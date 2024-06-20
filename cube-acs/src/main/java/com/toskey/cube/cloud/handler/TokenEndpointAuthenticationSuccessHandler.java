package com.toskey.cube.cloud.handler;

import com.toskey.cube.cloud.event.SaveLoginLogEvent;
import com.toskey.cube.cloud.token.TokenWrapper;
import com.toskey.cube.common.core.constant.CacheConstants;
import com.toskey.cube.common.core.util.ResponseUtils;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.core.util.WebUtils;
import com.toskey.cube.common.log.enums.LogResultType;
import com.toskey.cube.common.log.enums.LoginLogType;
import com.toskey.cube.common.tenant.component.TenantContextHolder;
import com.toskey.cube.service.log.interfaces.dto.LoginLogDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * TokenEndpointAuthenticationSuccessHandler
 *
 * @author toskey
 * @version 1.0.0
 */
@Component
public class TokenEndpointAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ApplicationEventPublisher applicationEventPublisher;

    public TokenEndpointAuthenticationSuccessHandler(RedisTemplate<String, Object> redisTemplate, ApplicationEventPublisher applicationEventPublisher) {
        this.redisTemplate = redisTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2AccessTokenAuthenticationToken authenticationToken = (OAuth2AccessTokenAuthenticationToken) authentication;
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        String username = request.getParameter(OAuth2ParameterNames.USERNAME);
        if (StringUtils.isBlank(username)) {
            Map<String, Object> additionalParameters = authenticationToken.getAdditionalParameters();
            username = (String) additionalParameters.get("username");
        }

        saveLog(username, grantType);

        redisTemplate.delete(buildCacheKey(username));

        ResponseUtils.writeJson(TokenWrapper.of(authenticationToken), response);
    }

    private void saveLog(String username, String grantType) {
        LoginLogDTO log = new LoginLogDTO();
        log.setType(LoginLogType.LOGIN.getValue());
        log.setGrantType(grantType);
        log.setResult(LogResultType.NORMAL.getValue());
        log.setClientIp(WebUtils.getIP());
        log.setCreateBy(username);
        log.setCreateTime(LocalDateTime.now());
        log.setTenantId(TenantContextHolder.getContext().getId());

        applicationEventPublisher.publishEvent(new SaveLoginLogEvent(log));
    }

    private String buildCacheKey(String username) {
        return String.format("%s:%s:%s:%s", CacheConstants.CACHE_GLOBAL_PREFIX,
                TenantContextHolder.getContext().getId(),
                CacheConstants.CACHE_LOGIN_ERRORS, username);
    }
}
