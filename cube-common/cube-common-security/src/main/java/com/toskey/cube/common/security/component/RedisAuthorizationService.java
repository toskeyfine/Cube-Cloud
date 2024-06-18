package com.toskey.cube.common.security.component;

import com.toskey.cube.common.core.constant.CacheConstants;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationCode;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.util.Assert;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * RedisAuthorizationService
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 10:42
 */
public class RedisAuthorizationService implements OAuth2AuthorizationService {

    private final RedisTemplate<String, Object> redisTemplate;


    public RedisAuthorizationService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        if (authorization.getAttribute(OAuth2ParameterNames.STATE) != null) {
            String state = authorization.getAttribute(OAuth2ParameterNames.STATE);
            redisTemplate.opsForValue().set(
                    buildTokenCacheKey(state, OAuth2ParameterNames.STATE),
                    authorization,
                    600, TimeUnit.SECONDS
            );
        }
        if (authorization.getToken(OAuth2AuthorizationCode.class) != null) {
            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCodeToken = authorization.getToken(OAuth2AuthorizationCode.class);
            OAuth2AuthorizationCode authorizationCode = authorizationCodeToken.getToken();
            long expires = ChronoUnit.SECONDS.between(authorizationCode.getIssuedAt(), authorizationCode.getExpiresAt());
            redisTemplate.opsForValue().set(
                    buildTokenCacheKey(authorizationCode.getTokenValue(), OAuth2ParameterNames.CODE),
                    authorization,
                    expires, TimeUnit.SECONDS
            );
        }
        if (authorization.getAccessToken() != null) {
            OAuth2Authorization.Token<OAuth2AccessToken> authorizationAccessToken = authorization.getAccessToken();
            OAuth2AccessToken accessToken = authorizationAccessToken.getToken();
            long expires = ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt());
            redisTemplate.opsForValue().set(
                    buildTokenCacheKey(accessToken.getTokenValue(), OAuth2ParameterNames.ACCESS_TOKEN),
                    authorization,
                    expires, TimeUnit.SECONDS
            );
        }
        if (authorization.getRefreshToken() != null) {
            OAuth2Authorization.Token<OAuth2RefreshToken> auth2RefreshTokenToken = authorization.getRefreshToken();
            OAuth2RefreshToken refreshToken = auth2RefreshTokenToken.getToken();
            long expires = ChronoUnit.SECONDS.between(refreshToken.getIssuedAt(), refreshToken.getExpiresAt());
            redisTemplate.opsForValue().set(
                    buildTokenCacheKey(refreshToken.getTokenValue(), OAuth2ParameterNames.REFRESH_TOKEN),
                    authorization,
                    expires, TimeUnit.SECONDS
            );
        }

    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        Set<String> removeKeys = new HashSet<>();
        if (authorization.getAttribute(OAuth2ParameterNames.STATE) != null) {
            String state = authorization.getAttribute(OAuth2ParameterNames.STATE);
            removeKeys.add(buildTokenCacheKey(state, OAuth2ParameterNames.STATE));
        }
        if (authorization.getToken(OAuth2AuthorizationCode.class) != null) {
            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCodeToken = authorization.getToken(OAuth2AuthorizationCode.class);
            OAuth2AuthorizationCode authorizationCode = authorizationCodeToken.getToken();
            removeKeys.add(buildTokenCacheKey(authorizationCode.getTokenValue(), OAuth2ParameterNames.CODE));
        }
        if (authorization.getAccessToken() != null) {
            OAuth2Authorization.Token<OAuth2AccessToken> authorizationAccessToken = authorization.getAccessToken();
            OAuth2AccessToken accessToken = authorizationAccessToken.getToken();
            removeKeys.add(buildTokenCacheKey(accessToken.getTokenValue(), OAuth2ParameterNames.ACCESS_TOKEN));
        }
        if (authorization.getRefreshToken() != null) {
            OAuth2Authorization.Token<OAuth2RefreshToken> auth2RefreshTokenToken = authorization.getRefreshToken();
            OAuth2RefreshToken refreshToken = auth2RefreshTokenToken.getToken();
            removeKeys.add(buildTokenCacheKey(refreshToken.getTokenValue(), OAuth2ParameterNames.REFRESH_TOKEN));
        }
        redisTemplate.delete(removeKeys);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return null;
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        return (OAuth2Authorization) redisTemplate.opsForValue().get(buildTokenCacheKey(token, tokenType.getValue()));
    }

    private String buildTokenCacheKey(String value, String type) {
        return String.format("%s:%s:%s:%s", CacheConstants.CACHE_GLOBAL_PREFIX, CacheConstants.CACHE_AUTHORIZATION_KEY, type, value);
    }


}
