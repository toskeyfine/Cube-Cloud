package com.toskey.cube.common.resource.server.annotation;

import java.lang.annotation.*;

/**
 * FeignApi
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 16:02
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface FeignApi {
}
