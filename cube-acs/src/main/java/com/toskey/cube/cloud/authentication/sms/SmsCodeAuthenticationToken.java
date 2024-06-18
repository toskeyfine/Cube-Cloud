package com.toskey.cube.cloud.authentication.sms;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * SmsCodeAuthenticationToken
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 10:07
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

    private final String mobile;

    private final String code;

    private final Authentication principal;

    private final Set<String> scopes;

    private final Map<String, Object> additionalParameters;

    public SmsCodeAuthenticationToken(String mobile,
                                      String code,
                                      Authentication principal,
                                      Set<String> scopes,
                                      Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        this.mobile = mobile;
        this.code = code;
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

    public String getMobile() {
        return mobile;
    }

    public String getCode() {
        return code;
    }

    public Set<String> getScopes() {
        return scopes;
    }

    public Map<String, Object> getAdditionalParameters() {
        return additionalParameters;
    }

}
