package com.toskey.cube.service.config.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.core.constant.SecurityConstants;
import com.toskey.cube.common.data.entity.DataEntity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Arrays;

/**
 * OAuth2Client
 *
 * @author toskey
 * @version 1.0.0
 */
@TableName("sys_oauth2_client")
public class OAuth2Client extends DataEntity {

    private String clientName;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 授权范围
     */
    private String scopes;

    /**
     * 认证类型
     */
    private String authorizedGrantTypes;

    /**
     * 从定向地址
     */
    private String redirectUris;

    /**
     * 是否自动同意
     */
    private Boolean autoApprove;

    /**
     * access_taken有效期（秒）
     */
    private Integer accessTokenValidity;

    /**
     * 刷新refresh_token有效期(秒)
     */
    private Integer refreshTokenValidity;

    public RegisteredClient toRegisteredClient() {
        RegisteredClient.Builder builder = RegisteredClient.withId(this.id)
                .clientName(this.clientName)
                .clientId(this.clientId)
                .clientSecret("{noop}" + this.clientSecret)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .clientSettings(
                        ClientSettings.builder()
                                .requireAuthorizationConsent(!this.autoApprove)
                                .requireProofKey(Boolean.TRUE)
                                .build()
                )
                .tokenSettings(
                        TokenSettings.builder()
                                .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                                .accessTokenTimeToLive(Duration.ofSeconds(this.accessTokenValidity))
                                .refreshTokenTimeToLive(Duration.ofSeconds(this.refreshTokenValidity))
                                .build()
                );

        Arrays.stream(StringUtils.delimitedListToStringArray(this.authorizedGrantTypes, ","))
                .map(AuthorizationGrantType::new)
                .forEach(builder::authorizationGrantType);

        Arrays.stream(StringUtils.delimitedListToStringArray(this.scopes, ","))
                .forEach(builder::scope);

        Arrays.stream(StringUtils.delimitedListToStringArray(this.redirectUris, ","))
                .forEach(builder::redirectUri);

        return builder.build();
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }


    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    public String getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
        this.authorizedGrantTypes = authorizedGrantTypes;
    }

    public String getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(String redirectUris) {
        this.redirectUris = redirectUris;
    }

    public Boolean getAutoApprove() {
        return autoApprove;
    }

    public void setAutoApprove(Boolean autoApprove) {
        this.autoApprove = autoApprove;
    }

    public Integer getAccessTokenValidity() {
        return accessTokenValidity;
    }

    public void setAccessTokenValidity(Integer accessTokenValidity) {
        this.accessTokenValidity = accessTokenValidity;
    }

    public Integer getRefreshTokenValidity() {
        return refreshTokenValidity;
    }

    public void setRefreshTokenValidity(Integer refreshTokenValidity) {
        this.refreshTokenValidity = refreshTokenValidity;
    }
}
