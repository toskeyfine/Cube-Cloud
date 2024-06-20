package com.toskey.cube.common.tenant;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.toskey.cube.common.tenant.component.TenantContextFilter;
import com.toskey.cube.common.tenant.component.TenantContextInterceptor;
import com.toskey.cube.common.tenant.component.TenantMybatisInterceptor;
import feign.RequestInterceptor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 自动装配
 *
 * @author toskey
 * @version 1.0.0
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
