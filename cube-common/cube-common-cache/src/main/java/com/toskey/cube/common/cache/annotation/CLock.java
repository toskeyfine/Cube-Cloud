package com.toskey.cube.common.cache.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * CLock
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/5/31 16:53
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CLock {

    String key();

    String prefix() default "";

    long expires() default 2000L;

    TimeUnit unit() default TimeUnit.MILLISECONDS;

    boolean tryLock() default false;

    long tryTime() default 0L;

    boolean fair() default false;

}
