package com.toskey.cube.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

/**
 * RateLimiterConfiguration
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/7/29 15:49
 */
@Configuration
public class RateLimiterConfiguration {

    private final static String REQUEST_HEADER_X_FORWARDED_FOR = "X-Forwarded-For";

    private final static String REQUEST_HEADER_AUTHORIZATION = "Authorization";

    @Bean(value = "pathKeyResolver")
    public KeyResolver pathKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getPath().toString());
    }

    @Bean(value = "remoteIpKeyResolver")
    public KeyResolver remoteIpKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getFirst(REQUEST_HEADER_X_FORWARDED_FOR));
    }

    @Bean(value = "remoteHostKeyResolver")
    public KeyResolver remoteHostKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

    @Bean(value = "tokenKeyResolver")
    public KeyResolver tokenKeyResolver() {
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getFirst(REQUEST_HEADER_AUTHORIZATION));
    }
}
