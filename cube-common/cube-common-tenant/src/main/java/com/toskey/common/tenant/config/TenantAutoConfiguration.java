package com.toskey.common.tenant.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.toskey.common.tenant.component.TenantContextFilter;
import com.toskey.common.tenant.component.TenantContextInterceptor;
import com.toskey.common.tenant.component.TenantMybatisInterceptor;
import feign.RequestInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.GenericFilterBean;

/**
 * TenantAutoConfiguration
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 14:55
 */
@AutoConfigureAfter(name = { "com.toskey.common.data.mybatis.config.MybatisPlusConfiguration" })
@RequiredArgsConstructor
@ConditionalOnBean(MybatisPlusInterceptor.class)
public class TenantAutoConfiguration {

    private final MybatisPlusInterceptor mybatisPlusInterceptor;

    @PostConstruct
    public void initialize() {
        mybatisPlusInterceptor.addInnerInterceptor(tenantMybatisInterceptor());
    }

    @Bean
    public TenantMybatisInterceptor tenantMybatisInterceptor() {
        return new TenantMybatisInterceptor();
    }

    @Bean
    public RequestInterceptor tenantContextInterceptor() {
        return new TenantContextInterceptor();
    }

    @Bean
    public GenericFilterBean tenantContextFilter() {
        return new TenantContextFilter();
    }

}
