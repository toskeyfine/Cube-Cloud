package com.toskey.cube.cloud.authentication.password;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * PasswordAuthenticationToken
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 9:59
 */
public class PasswordAuthenticationToken extends AbstractAuthenticationToken {

    private final String username;

    private final String password;

    private final Authentication principal;

    private final Set<String> scopes;

    private final Map<String, Object> additionalParameters;

    public PasswordAuthenticationToken(String username,
                                       String password,
                                       Authentication principal,
                                       Set<String> scopes,
                                       Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        this.username = username;
        this.password = password;
        this.principal = principal;
        this.scopes = scopes;
        this.additionalParameters = additionalParameters;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public Map<String, Object> getAdditionalParameters() {
        return additionalParameters;
    }
}
