package com.toskey.cube.common.resource.server.component;

import com.toskey.cube.common.core.util.SpringContextHolder;
import com.toskey.cube.common.resource.server.principal.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 10:40
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

        Map<String, CubeUserDetailsService> userDetailsServiceMap = SpringContextHolder.getBeansOfType(CubeUserDetailsService.class);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) authorization.getAttributes().get(Principal.class.getName());
        LoginUser loginUser = (LoginUser) usernamePasswordAuthenticationToken.getPrincipal();

        UserDetails userDetails = userDetailsServiceMap.values().stream()
                .filter(userDetailsService -> userDetailsService.support(authorization.getAuthorizationGrantType()))
                .findFirst()
                .map(userDetailsService -> userDetailsService.loadUserByUsername(loginUser.getUsername()))
                .orElseThrow(() -> new UserPrincipalNotFoundException(loginUser.getUsername()));

        return (LoginUser) userDetails;
    }

}
