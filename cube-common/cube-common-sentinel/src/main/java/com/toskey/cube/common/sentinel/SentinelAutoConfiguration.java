package com.toskey.cube.common.sentinel;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.toskey.cube.common.sentinel.component.EnhanceSentinelFeign;
import com.toskey.cube.common.sentinel.component.GlobalExceptionAdvice;
import com.toskey.cube.common.sentinel.component.RequestOriginHeaderParser;
import com.toskey.cube.common.sentinel.component.SentinelExceptionHandler;
import feign.Feign;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * SentinelAutoConfiguration
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/19 14:31
 */
@AutoConfigureBefore({ SentinelFeignAutoConfiguration.class })
public class SentinelAutoConfiguration {

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "feign.sentinel.enabled", matchIfMissing = true)
    public Feign.Builder sentinelFeignBuilder() {
        return EnhanceSentinelFeign.builder();
    }

    @Bean
    @ConditionalOnMissingBean
    public RequestOriginParser requestOriginParser() {
        return new RequestOriginHeaderParser();
    }

    @Bean
    public GlobalExceptionAdvice globalExceptionAdvice() {
        return new GlobalExceptionAdvice();
    }

    @Bean
    @ConditionalOnMissingBean
    public BlockExceptionHandler blockExceptionHandler() {
        return new SentinelExceptionHandler();
    }
}
