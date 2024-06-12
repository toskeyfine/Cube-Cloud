package com.toskey.cube.common.resource.server.component;

import com.toskey.cube.common.core.util.WebUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.Optional;

/**
 * TransferTokenInterceptor
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 17:26
 */
@RequiredArgsConstructor
public class TransferTokenInterceptor implements RequestInterceptor {

    private final CubeBearerTokenResolver tokenResolver;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        Optional.of(tokenResolver.resolve(WebUtils.getRequest()))
                .ifPresent(token ->
                        requestTemplate.header(
                                HttpHeaders.AUTHORIZATION,
                                String.format("%s %s", OAuth2AccessToken.TokenType.BEARER, token)
                        )
                );
    }

}
