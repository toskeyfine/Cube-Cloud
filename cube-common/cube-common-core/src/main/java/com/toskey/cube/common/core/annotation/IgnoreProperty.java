package com.toskey.cube.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IgnoreProperty
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/11 10:28
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreProperty {

    Strategy[] ignore() default Strategy.ALL;

    enum Strategy {
        ALL,
        TO_ENTITY,
        TO_MAPPER
    }

}
