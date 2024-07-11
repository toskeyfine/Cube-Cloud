package com.toskey.cube.common.resource.server.component;

import com.toskey.cube.common.core.constant.SecurityConstants;
import com.toskey.cube.common.core.util.SpringContextHolder;
import com.toskey.cube.common.security.principal.LoginUser;
import com.toskey.cube.common.security.service.CubeUserDetailsService;
import com.toskey.cube.common.security.service.PasswordUserDetailsServiceImpl;
import com.toskey.cube.common.security.service.SmsUserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Principal;
import java.util.Map;

/**
 * CubeOpaqueTokenIntrospector
 *
 * @author toskey
 * @version 1.0.0
 */
@RequiredArgsConstructor
public class CubeOpaqueTokenIntrospector implements OpaqueTokenIntrospector {

    private final OAuth2AuthorizationService authorizationService;

    @SneakyThrows
    @Override
    public OAuth2AuthenticatedPrincipal introspect(String token) {
        OAuth2Authorization authorization = authorizationService.findByToken(token, OAuth2TokenType.ACCESS_TOKEN);
        if (authorization == null) {
            throw new InvalidBearerTokenException(token);
        }

        UserDetailsService userDetailsService = null;
        if (authorization.getAuthorizationGrantType().getValue().equals(SecurityConstants.GRANT_TYPE_SMS)) {
            userDetailsService = SpringContextHolder.getBean(SmsUserDetailsServiceImpl.class);
        } else if (authorization.getAuthorizationGrantType().equals(AuthorizationGrantType.PASSWORD)) {
            userDetailsService = SpringContextHolder.getBean(PasswordUserDetailsServiceImpl.class);
        } else {
            throw new ClassNotFoundException("UserDetailsService not found");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(authorization.getPrincipalName());
        if (userDetails == null) {
            throw new UserPrincipalNotFoundException(authorization.getPrincipalName());
        }

        return (LoginUser) userDetails;
    }

}
