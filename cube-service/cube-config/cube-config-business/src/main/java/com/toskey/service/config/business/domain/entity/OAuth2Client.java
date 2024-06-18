package com.toskey.service.config.business.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.toskey.cube.common.data.entity.DataEntity;

/**
 * OAuth2Client
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 17:33
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
     * 资源ids
     */
    private String resourceIds;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 认证类型
     */
    private String authorizedGrantTypes;

    /**
     * 从定向地址
     */
    private String redirectUris;

    /**
     * 权限
     */
    private String authorities;

    /**
     * 扩展
     */
    private String extensions;

    /**
     * 是否自动同意
     */
    private Boolean autoapprove;

    /**
     * access_taken有效期（秒）
     */
    private Integer accessTokenValidity;

    /**
     * 刷新refresh_token有效期(秒)
     */
    private Integer refreshTokenValidity;

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

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
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

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getExtensions() {
        return extensions;
    }

    public void setExtensions(String extensions) {
        this.extensions = extensions;
    }

    public Boolean getAutoapprove() {
        return autoapprove;
    }

    public void setAutoapprove(Boolean autoapprove) {
        this.autoapprove = autoapprove;
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
