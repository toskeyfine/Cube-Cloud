package com.toskey.cube.gateway.filter;

import org.springframework.cloud.gateway.filter.headers.HttpHeadersFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;

/**
 * 去除重复响应头
 *
 * @author toskey
 * @version 1.0.0
 */
@Component
public class RepeatHeaderFilter implements HttpHeadersFilter {
    @Override
    public HttpHeaders filter(HttpHeaders input, ServerWebExchange exchange) {
        if (CorsUtils.isCorsRequest(exchange.getRequest())) {
            //移除重复的header
            exchange.getResponse().getHeaders().forEach((s, strings) -> {
                input.remove(s);
            });
        }
        return input;
    }

    @Override
    public boolean supports(Type type) {
        return type.equals(Type.RESPONSE);
    }
}
