package com.toskey.common.tenant.component;

import jakarta.annotation.Nonnull;

/**
 * TenantContext
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 16:19
 */
public final class TenantContext {

    private final String id;

    private TenantContext(String id) {
        this.id = id;
    }

    public static TenantContext of(@Nonnull String id) {
        return new TenantContext(id);
    }

    public String getId() {
        return id;
    }

}