package com.toskey.cube.common.tenant.component;

import jakarta.annotation.Nonnull;

/**
 * 租户上下文
 *
 * @author toskey
 * @version 1.0.0
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