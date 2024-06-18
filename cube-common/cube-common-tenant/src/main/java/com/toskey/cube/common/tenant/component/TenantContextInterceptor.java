package com.toskey.cube.common.tenant.component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Optional;

/**
 * TenantContextInterceptor
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 16:48
 */
public class TenantContextInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Optional.of(TenantContextHolder.getContext())
                .ifPresent(context ->
                        requestTemplate.header("TENANT-ID", context.getId())
                );
    }
}
