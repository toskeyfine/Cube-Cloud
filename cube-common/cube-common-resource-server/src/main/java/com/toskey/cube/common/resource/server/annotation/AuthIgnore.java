package com.toskey.cube.common.resource.server.annotation;

import java.lang.annotation.*;

/**
 * FeignApi
 *
 * @author toskey
 * @version 1.0.0
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AuthIgnore {
}
