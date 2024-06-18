package com.toskey.cube.gateway.config;

import com.toskey.cube.gateway.captcha.ImageCodeCheckHandler;
import com.toskey.cube.gateway.captcha.ImageCodeCreateHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.*;

/**
 * 路由配置信息
 *
 * @author toskey
 * @version 1.0.0
 */
@Configuration
@RequiredArgsConstructor
public class RouterFunctionConfiguration {

    private final ImageCodeCreateHandler imageCodeCreateHandler;

    private final ImageCodeCheckHandler imageCodeCheckHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route(
                        RequestPredicates.path("/code/create")
                                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), imageCodeCreateHandler)
                .andRoute(
                        RequestPredicates.POST("/code/check")
                                .and(RequestPredicates.accept(MediaType.ALL)), imageCodeCheckHandler);
    }
}
