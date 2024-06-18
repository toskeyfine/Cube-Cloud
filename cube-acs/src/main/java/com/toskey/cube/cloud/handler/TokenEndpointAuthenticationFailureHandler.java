package com.toskey.cube.cloud.handler;

import com.toskey.cube.common.tenant.component.TenantContextHolder;
import com.toskey.cube.cloud.event.SaveLoginLogEvent;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CacheConstants;
import com.toskey.cube.common.core.constant.SecurityConstants;
import com.toskey.cube.common.core.util.ResponseUtils;
import com.toskey.cube.common.core.util.WebUtils;
import com.toskey.cube.common.log.enums.LogResultType;
import com.toskey.cube.common.log.enums.LoginLogType;
import com.toskey.cube.service.log.interfaces.dto.LoginLogDTO;
import com.toskey.cube.service.sas.interfaces.service.RemoteUserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * TokenEndpointAuthenticationFailureHandler
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/14 10:35
 */
@Component
public class TokenEndpointAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final RemoteUserService remoteUserService;

    public TokenEndpointAuthenticationFailureHandler(final RedisTemplate<String, Object> redisTemplate,
                                            final ApplicationEventPublisher applicationEventPublisher,
                                            final RemoteUserService remoteUserService) {
        this.redisTemplate = redisTemplate;
        this.applicationEventPublisher = applicationEventPublisher;
        this.remoteUserService = remoteUserService;
    }
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (OAuth2ParameterNames.PASSWORD.equals(grantType)) {
            String username = request.getParameter(OAuth2ParameterNames.USERNAME);

            if (exception instanceof OAuth2AuthenticationException) {
                String cacheKey = buildCacheKey(username);
                long cacheErrorTimes = redisTemplate.opsForValue().increment(cacheKey);
                redisTemplate.expire(cacheKey, CacheConstants.EXPIRE_LOGIN_ERRORS, TimeUnit.MILLISECONDS);
                if (cacheErrorTimes >= SecurityConstants.LOGIN_ERROR_MAX_TIMES) {
                    remoteUserService.lock(username);
                }
            }

            saveLog(username, grantType, exception);
        }
        ResponseUtils.writeJson(RestResult.failure(exception.getLocalizedMessage(), null), response);
    }

    private void saveLog(String username, String grantType, AuthenticationException exception) {
        LoginLogDTO log = new LoginLogDTO();
        log.setType(LoginLogType.LOGIN.getValue());
        log.setGrantType(grantType);
        log.setResult(LogResultType.ERROR.getValue());
        log.setClientIp(WebUtils.getIP());
        log.setReason(exception.getLocalizedMessage());
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
