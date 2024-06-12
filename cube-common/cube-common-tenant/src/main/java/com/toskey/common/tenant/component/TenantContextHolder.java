package com.toskey.common.tenant.component;


import lombok.experimental.UtilityClass;

/**
 * TenantContextHolder
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 16:16
 */
@UtilityClass
public final class TenantContextHolder {

    private static final ThreadLocal<TenantContext> TENANT_HOLDER = new ThreadLocal<>();

    public void setContext(TenantContext context) {
        TENANT_HOLDER.set(context);
    }

    public TenantContext getContext() {
        return TENANT_HOLDER.get();
    }

    public void clear() {
        TENANT_HOLDER.remove();
    }

}
