package com.toskey.cube.cloud.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * AuthorizationServerProperties
 *
 * @author toskey
 * @version 1.0.0
 */
@ConfigurationProperties("cube.authorization.server")
public class AuthorizationServerProperties {

    private String[] ignorePaths = {"/oauth2/**", "/token/**", "/login/**", "/error"};

    private String loginEntryPoint;

    private String issuerUrl;

    public String[] getIgnorePaths() {
        return ignorePaths;
    }

    public void setIgnorePaths(String[] ignorePaths) {
        this.ignorePaths = ignorePaths;
    }

    public String getLoginEntryPoint() {
        return loginEntryPoint;
    }

    public void setLoginEntryPoint(String loginEntryPoint) {
        this.loginEntryPoint = loginEntryPoint;
    }

    public String getIssuerUrl() {
        return issuerUrl;
    }

    public void setIssuerUrl(String issuerUrl) {
        this.issuerUrl = issuerUrl;
    }

}
