package com.toskey.cube.service.config.business.vo.client;

import com.toskey.cube.common.core.annotation.EntityMapper;
import com.toskey.cube.common.core.base.BaseEntityMapper;
import com.toskey.cube.service.config.business.domain.entity.OAuth2Client;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

/**
 * OAuth2ClientFormVO
 *
 * @author toskey
 * @version 1.0.0
 */
@EntityMapper(entity = OAuth2Client.class)
public class ClientFormVO extends BaseEntityMapper {

    private String id;

    @NotBlank
    @Size(min = 2, max = 20)
    private String clientName;

    /**
     * 客户端id
     */
    @NotBlank
    @Size(min = 10, max = 64)
    private String clientId;

    /**
     * 资源ids
     */
    private String resourceIds;

    /**
     * 授权范围
     */
    @NotBlank
    private String scopes;

    /**
     * 认证类型
     */
    @NotBlank
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
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

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
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
