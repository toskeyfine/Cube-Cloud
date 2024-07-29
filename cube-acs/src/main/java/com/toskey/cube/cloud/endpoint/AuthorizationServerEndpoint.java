package com.toskey.cube.cloud.endpoint;

import com.toskey.cube.cloud.event.SaveLoginLogEvent;
import com.toskey.cube.cloud.handler.ClientAuthenticationFailureHandler;
import com.toskey.cube.common.core.base.RestResult;
import com.toskey.cube.common.core.constant.CacheConstants;
import com.toskey.cube.common.core.constant.CommonConstants;
import com.toskey.cube.common.core.constant.SecurityConstants;
import com.toskey.cube.common.core.util.StringUtils;
import com.toskey.cube.common.core.util.WebUtils;
import com.toskey.cube.common.log.enums.LogResultType;
import com.toskey.cube.common.log.enums.LoginLogType;
import com.toskey.cube.common.security.principal.LoginUser;
import com.toskey.cube.common.tenant.component.TenantContextHolder;
import com.toskey.cube.service.log.interfaces.dto.LoginLogDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * AuthorizationServerEndpoint
 *
 * @author toskey
 * @version 1.0.0
 */
@AllArgsConstructor
@RestController
@RequestMapping("/acs")
public class AuthorizationServerEndpoint {

    private final OAuth2AuthorizationService authorizationService;

    private final ClientAuthenticationFailureHandler authenticationFailureHandler;

    private final RedisTemplate<String, Object> redisTemplate;

    private final ApplicationEventPublisher applicationEventPublisher;

    @GetMapping("/{token}/check")
    public RestResult<Void> checkToken(@PathVariable("token") String token, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (StringUtils.isBlank(token)) {
            authenticationFailureHandler.onAuthenticationFailure(
                    request,
                    response,
                    new InvalidBearerTokenException("empty_token")
            );
            return null;
        }
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization == null || authorization.getAccessToken() == null) {
            authenticationFailureHandler.onAuthenticationFailure(
                    request,
                    response,
                    new InvalidBearerTokenException(OAuth2ErrorCodes.INVALID_TOKEN)
            );
            return null;
        }
        return RestResult.success();
    }

    @DeleteMapping("/logout")
    public RestResult<Void> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String header) {
        if (StringUtils.isBlank(header)) {
            return RestResult.success();
        }
        String token = header.replace(OAuth2AccessToken.TokenType.BEARER.getValue(), StringUtils.EMPTY).trim();
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization != null) {
            if (removeToken(authorization)) {
                saveLogoutLog(authorization, true, true);
                return RestResult.failure();
            }
        }
        saveLogoutLog(authorization, false, true);
        return RestResult.failure();
    }

    @DeleteMapping("/{token}/kickout")
    public RestResult<Void> kickout(@PathVariable("token") String token) {
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization != null) {
            if (removeToken(authorization)) {
                saveLogoutLog(authorization, true,false);
                return RestResult.success();
            }
        }
        saveLogoutLog(authorization, false,false);
        return RestResult.failure();
    }

    private boolean removeToken(OAuth2Authorization authorization) {
        OAuth2Authorization.Token<OAuth2AccessToken> accessToken = authorization.getAccessToken();
        if (accessToken == null || StringUtils.isBlank(accessToken.getToken().getTokenValue())) {
            return false;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = authorization.getAttribute("java.security.Principal");
        if (usernamePasswordAuthenticationToken == null) {
            return false;
        }
        // 清空用户信息
        redisTemplate.delete(buildCacheKey(authorization.getPrincipalName()));
        // 清空access token
        authorizationService.remove(authorization);
        return true;
    }

    private String buildCacheKey(String username) {
        return String.format("%s:login_user:%s", CacheConstants.CACHE_GLOBAL_PREFIX, username);
    }

    /**
     * 异步存储退出登录日志
     *
     * @param authorization
     * @param isSuccess
     * @param isActive
     */
    private void saveLogoutLog(OAuth2Authorization authorization, boolean isSuccess, boolean isActive) {
        LoginLogDTO log = new LoginLogDTO();
        if (isActive) {
            log.setType(LoginLogType.LOGOUT.getValue());
            log.setClientIp(WebUtils.getIP());
        } else {
            log.setType(LoginLogType.KICKOUT.getValue());
        }
        if (isSuccess) {
            log.setResult(LogResultType.NORMAL.getValue());
        } else {
            log.setResult(LogResultType.ERROR.getValue());
        }
        log.setCreateBy(authorization.getPrincipalName());
        log.setCreateTime(LocalDateTime.now());
        log.setTenantId(TenantContextHolder.getContext().getId());
        // 发送异步日志事件
        applicationEventPublisher.publishEvent(new SaveLoginLogEvent(log));
    }
}
