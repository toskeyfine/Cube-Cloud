package com.toskey.cube.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EntityProperty
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/11 10:27
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityProperty {

    String target() default "";

}
