package com.toskey.cube.common.cache.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * CLock
 *
 * @author toskey
 * @version 1.0.0
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
