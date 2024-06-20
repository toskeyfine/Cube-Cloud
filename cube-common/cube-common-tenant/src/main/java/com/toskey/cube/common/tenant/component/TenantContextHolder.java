package com.toskey.cube.common.tenant.component;


import lombok.experimental.UtilityClass;

/**
 * 存储租户上下文
 *
 * @author toskey
 * @version 1.0.0
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
