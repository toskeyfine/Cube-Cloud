package com.toskey.cube.common.core.annotation;

import com.toskey.cube.common.core.base.BaseEntity;

import java.lang.annotation.*;

/**
 * EntityMapper
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/12 15:31
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EntityMapper {

    Class<? extends BaseEntity> entity() default BaseEntity.class;

}
