package com.toskey.cube.common.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存延迟双删注解
 *
 * @author toskey
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CacheClearDelay {

    String prefix() default "";

    String key() default "";

    long delay() default 500L;

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

}
