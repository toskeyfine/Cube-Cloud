package com.toskey.cube.cloud.token;

import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;

import java.time.Instant;
import java.util.Map;

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

    public static TokenWrapper of(OAuth2AccessTokenResponse tokenResponse) {
        TokenWrapper tokenWrapper = new TokenWrapper();
        tokenWrapper.setAccessToken(tokenResponse.getAccessToken().getTokenValue());
        if (tokenResponse.getRefreshToken() != null) {
            tokenWrapper.setRefreshToken(tokenResponse.getRefreshToken().getTokenValue());
        }
        tokenWrapper.setIssuedAt(tokenResponse.getAccessToken().getIssuedAt());
        tokenWrapper.setExpiredAt(tokenResponse.getAccessToken().getExpiresAt());
        tokenWrapper.setExtensions(tokenResponse.getAdditionalParameters());
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
