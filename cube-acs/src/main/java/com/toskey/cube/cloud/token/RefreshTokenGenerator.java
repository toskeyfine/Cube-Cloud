package com.toskey.cube.cloud.token;

import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

import java.time.Instant;
import java.util.UUID;

/**
 * RefreshToken Generator
 * <br>
 * 重写OAuth2TokenGenerator, 将RefreshToken改为UUID
 *
 * @author toskey
 * @version 1.0.0
 * @see org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator
 */
public class RefreshTokenGenerator implements OAuth2TokenGenerator<OAuth2RefreshToken> {
    @Override
    public OAuth2RefreshToken generate(OAuth2TokenContext context) {
        if (!OAuth2TokenType.REFRESH_TOKEN.equals(context.getTokenType())) {
            return null;
        }
        if (isPublicClientForAuthorizationCodeGrant(context)) {
            // Do not issue refresh token to public client
            return null;
        }

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(context.getRegisteredClient().getTokenSettings().getRefreshTokenTimeToLive());
        return new OAuth2RefreshToken(UUID.randomUUID().toString(), issuedAt, expiresAt);
    }

    private static boolean isPublicClientForAuthorizationCodeGrant(OAuth2TokenContext context) {
        // @formatter:off
        if (AuthorizationGrantType.AUTHORIZATION_CODE.equals(context.getAuthorizationGrantType()) &&
                (context.getAuthorizationGrant().getPrincipal() instanceof OAuth2ClientAuthenticationToken clientPrincipal)) {
            return clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE);
        }
        // @formatter:on
        return false;
    }
}
