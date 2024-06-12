package com.toskey.cube.common.resource.server.component;

import com.toskey.cube.common.resource.server.config.AuthIgnoreProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * CubeBearerTokenResolver
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 10:22
 */
@RequiredArgsConstructor
public class CubeBearerTokenResolver implements BearerTokenResolver {

    private final AuthIgnoreProperties authIgnoreProperties;

    @Override
    public String resolve(HttpServletRequest request) {
        String requestPath = getRequestPath(request);
        if (needResolver(requestPath)) {
            return resolveTokenFromRequestHeader(request);
        }
        return null;
    }

    private String getRequestPath(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        return requestUri.substring(request.getContextPath().length());
    }

    private boolean needResolver(final String requestPath) {
        PathMatcher pathMatcher = new AntPathMatcher();
        return authIgnoreProperties.getIgnoreUrls().stream()
                .noneMatch(url -> pathMatcher.match(url, requestPath));
    }

    private String resolveTokenFromRequestHeader(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(authorizationHeader)
                && StringUtils.startsWithIgnoreCase(authorizationHeader, "bearer")) {
            Pattern pattern = Pattern.compile("^Bearer (?<token>[a-zA-Z0-9-:._~+/]+=*)$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(authorizationHeader);
            if (!matcher.matches()) {
                throw new OAuth2AuthenticationException(BearerTokenErrors.invalidToken("Can not resolve token from request header."));
            }
            return matcher.group("token");
        }
        return null;
    }
}
