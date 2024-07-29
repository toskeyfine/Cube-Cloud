package com.toskey.cube.common.core.annotation;

import com.toskey.cube.common.core.base.BaseEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * MapperProperty
 *
 * @author toskey
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapperProperty {

    String targetName() default "";

    Class<? extends BaseEntity> target() default BaseEntity.class;

}
