package com.toskey.cube.cloud.token;

import com.toskey.cube.common.core.constant.SecurityConstants;
import com.toskey.cube.common.security.principal.LoginUser;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * TokenCustomizer
 *
 * @author toskey
 * @version 1.0.0
 */
public class TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {
    @Override
    public void customize(OAuth2TokenClaimsContext context) {
        OAuth2TokenClaimsSet.Builder claims = context.getClaims();

        LoginUser loginUser = (LoginUser) context.getPrincipal().getPrincipal();
        claims.claim(SecurityConstants.USER_ID, loginUser.getId());
        claims.claim(SecurityConstants.USERNAME, loginUser.getUsername());
        claims.claim(SecurityConstants.TENANT_ID, loginUser.getTenantId());

    }
}
