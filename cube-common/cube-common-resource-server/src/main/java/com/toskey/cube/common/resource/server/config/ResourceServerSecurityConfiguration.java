package com.toskey.cube.common.resource.server.config;

import com.toskey.cube.common.resource.server.component.CubeBearerTokenResolver;
import com.toskey.cube.common.resource.server.component.ResourceServerExceptionEntryPoint;
import com.toskey.cube.common.security.component.RequestAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * ResourceServerConfiguration
 *
 * @author toskey
 * @version 1.0.0
 */
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthIgnoreProperties.class)
public class ResourceServerSecurityConfiguration {

    private final RequestAccessDeniedHandler accessDeniedHandler;

    private final CubeBearerTokenResolver bearerTokenResolver;

    private final OpaqueTokenIntrospector opaqueTokenIntrospector;

    private final ResourceServerExceptionEntryPoint resourceServerExceptionEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthIgnoreProperties ignoreProperties) throws Exception {
        http.authorizeHttpRequests(requests ->
                        requests.requestMatchers(
                                        ignoreProperties.getUrls()
                                                .stream()
                                                .map(AntPathRequestMatcher::new)
                                                .toArray(AntPathRequestMatcher[]::new)
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .oauth2ResourceServer(resourceServer ->
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
