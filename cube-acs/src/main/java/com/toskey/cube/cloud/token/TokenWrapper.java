package com.toskey.cube.cloud.token;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Token响应封装
 *
 * @author toskey
 * @version 1.0.0
 */
public class TokenWrapper {

    private String accessToken;

    private String refreshToken;

    private Instant issuedAt;

    private Instant expiredAt;

    private Map<String, Object> extensions;

    private TokenWrapper() {

    }

    public static TokenWrapper of(OAuth2AccessTokenAuthenticationToken accessTokenAuthentication) {
        OAuth2AccessToken accessToken = accessTokenAuthentication.getAccessToken(); // 访问令牌对象
        OAuth2RefreshToken refreshToken = accessTokenAuthentication.getRefreshToken(); // 刷新令牌对象
        Map<String, Object> additionalParameters = accessTokenAuthentication.getAdditionalParameters(); // 额外参数

        TokenWrapper tokenWrapper = new TokenWrapper();

        tokenWrapper.setAccessToken(accessToken.getTokenValue());
        if (refreshToken != null) {
            tokenWrapper.setRefreshToken(refreshToken.getTokenValue());
        }
        tokenWrapper.setIssuedAt(accessToken.getIssuedAt());
        tokenWrapper.setExpiredAt(accessToken.getExpiresAt());
        Map<String, Object> extensions = additionalParameters.entrySet().stream()
                .filter(entry -> entry.getKey().equals("tenant-id")
                        || entry.getKey().equals("username")
                        || entry.getKey().equals("user-id")
                        || entry.getKey().equals("scope"))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        tokenWrapper.setExtensions(extensions);
        return tokenWrapper;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(Instant issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Instant getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Instant expiredAt) {
        this.expiredAt = expiredAt;
    }

    public Map<String, Object> getExtensions() {
        return extensions;
    }

    public void setExtensions(Map<String, Object> extensions) {
        this.extensions = extensions;
    }
}
