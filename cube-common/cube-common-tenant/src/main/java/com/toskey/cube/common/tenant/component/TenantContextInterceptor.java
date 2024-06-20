package com.toskey.cube.common.tenant.component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Optional;

/**
 * Feign调用时租户上下文传递
 *
 * @author toskey
 * @version 1.0.0
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
