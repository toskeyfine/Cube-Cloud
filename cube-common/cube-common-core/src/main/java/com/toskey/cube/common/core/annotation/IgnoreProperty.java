package com.toskey.cube.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IgnoreProperty
 *
 * @author toskey
 * @version 1.0
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
