package com.toskey.cube.common.resource.server.config;

import com.toskey.cube.common.resource.server.component.CubeAccessDeniedHandler;
import com.toskey.cube.common.resource.server.component.CubeBearerTokenResolver;
import com.toskey.cube.common.resource.server.component.ResourceServerExceptionEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ResourceServerConfiguration
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 17:23
 */
@RequiredArgsConstructor
public class ResourceServerSecurityConfiguration {

    private final CubeAccessDeniedHandler accessDeniedHandler;

    private final CubeBearerTokenResolver bearerTokenResolver;

    private final OpaqueTokenIntrospector opaqueTokenIntrospector;

    private final ResourceServerExceptionEntryPoint resourceServerExceptionEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.oauth2ResourceServer(resourceServer ->
                resourceServer.accessDeniedHandler(accessDeniedHandler)
                        .bearerTokenResolver(bearerTokenResolver)
                        .authenticationEntryPoint(resourceServerExceptionEntryPoint)
                        .opaqueToken(opaqueToken ->
                                opaqueToken.introspector(opaqueTokenIntrospector)
                        )
                )
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }
}
