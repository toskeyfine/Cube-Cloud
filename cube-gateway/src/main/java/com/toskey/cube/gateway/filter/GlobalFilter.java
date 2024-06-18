package com.toskey.cube.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

/**
 * GlobalFilter
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 16:06
 */
public class GlobalFilter implements org.springframework.cloud.gateway.filter.GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest().mutate().headers(headers -> {
            headers.put("REQUEST-START-TIME", Collections.singletonList(String.valueOf(System.currentTimeMillis())));
        }).build();

        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
