package com.toskey.cube.gateway.filter;

import com.alibaba.nacos.common.http.param.MediaType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toskey.cube.common.core.base.RestResult;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * AuthenticationFilter
 *
 * @author toskey
 * @version 1.0.0
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final ObjectMapper objectMapper;

    @Override
    public GatewayFilter apply(Config config) {
        AuthenticationGatewayFilter filter = new AuthenticationGatewayFilter(objectMapper);
        Integer order = config.getOrder();
        boolean enabled = config.getEnabled();
        if (!enabled) {
            return (exchange, chain) -> chain.filter(exchange);
        }
        if (order == null) {
            return filter;
        }
        return new OrderedGatewayFilter(filter, order);
    }

    @Override
    public String name() {
        return "AuthenticationFilter";
    }

    @AllArgsConstructor
    public static class AuthenticationGatewayFilter implements GatewayFilter {

        private final ObjectMapper objectMapper;

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
            ServerHttpRequest request = exchange.getRequest();
            if (StringUtils.isBlank(request.getHeaders().getFirst("Authorization"))) {
                ServerHttpResponse response = exchange.getResponse();
                response.getHeaders().setConnection(MediaType.APPLICATION_JSON);
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                try {
                    return response.writeWith(
                            Mono.just(
                                    response.bufferFactory()
                                            .wrap(objectMapper.writeValueAsBytes(RestResult.failure()))
                            )
                    );
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            return chain.filter(exchange);
        }
    }

    public static class Config {

        private Integer order;

        private Boolean enabled;

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public Boolean getEnabled() {
            return enabled;
        }

        public void setEnabled(Boolean enabled) {
            this.enabled = enabled;
        }
    }
}
