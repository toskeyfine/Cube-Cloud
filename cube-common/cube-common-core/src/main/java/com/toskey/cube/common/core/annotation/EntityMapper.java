package com.toskey.cube.common.core.annotation;

import com.toskey.cube.common.core.base.BaseEntity;

import java.lang.annotation.*;

/**
 * EntityMapper
 *
 * @author toskey
 * @version 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityMapper {

    Class<? extends BaseEntity> entity() default BaseEntity.class;

}
