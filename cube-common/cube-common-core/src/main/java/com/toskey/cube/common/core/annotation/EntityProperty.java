package com.toskey.cube.common.core.annotation;

import com.toskey.cube.common.core.base.BaseEntityMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EntityProperty
 *
 * @author toskey
 * @version 1.0.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityProperty {

    String targetName() default "";

    Class<? extends BaseEntityMapper> target() default BaseEntityMapper.class;

    int strategy();

    interface Strategy {
        int QUERY = 1;
        int RESULT = 2;
        int FORM = 3;
    }

}
