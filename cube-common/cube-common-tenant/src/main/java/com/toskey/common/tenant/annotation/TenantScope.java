package com.toskey.common.tenant.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * TenantScope
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:07
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TenantScope {

    boolean enabled() default true;

}
