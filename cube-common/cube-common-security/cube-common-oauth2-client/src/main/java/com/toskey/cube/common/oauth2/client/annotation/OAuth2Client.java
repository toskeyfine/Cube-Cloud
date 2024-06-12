package com.toskey.cube.common.oauth2.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * OAuth2Client
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 17:57
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OAuth2Client {
}
