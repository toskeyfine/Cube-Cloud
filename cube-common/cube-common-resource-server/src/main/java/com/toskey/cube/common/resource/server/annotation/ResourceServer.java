package com.toskey.cube.common.resource.server.annotation;

import com.toskey.cube.common.resource.server.config.ResourceServerAutoConfiguration;
import com.toskey.cube.common.resource.server.config.ResourceServerSecurityConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ResourceServer
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 17:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ ResourceServerAutoConfiguration.class, ResourceServerSecurityConfiguration.class })
public @interface ResourceServer {
}
