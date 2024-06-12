package com.toskey.cube.common.resource.server.config;

import com.toskey.cube.common.resource.server.component.CubeAccessDeniedHandler;
import com.toskey.cube.common.resource.server.component.CubeBearerTokenResolver;
import com.toskey.cube.common.resource.server.component.CubeOpaqueTokenIntrospector;
import com.toskey.cube.common.resource.server.component.ResourceServerExceptionEntryPoint;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;

/**
 * ResourceServerAutoConfiguration
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 17:23
 */
@EnableConfigurationProperties(AuthIgnoreProperties.class)
public class ResourceServerAutoConfiguration {

    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector(OAuth2AuthorizationService authorizationService) {
        return new CubeOpaqueTokenIntrospector(authorizationService);
    }

    @Bean
    public CubeAccessDeniedHandler accessDeniedHandler() {
        return new CubeAccessDeniedHandler();
    }

    @Bean
    public CubeBearerTokenResolver bearerTokenResolver(AuthIgnoreProperties authIgnoreProperties) {
        return new CubeBearerTokenResolver(authIgnoreProperties);
    }

    @Bean
    public ResourceServerExceptionEntryPoint resourceServerExceptionEntryPoint() {
        return new ResourceServerExceptionEntryPoint();
    }

}
