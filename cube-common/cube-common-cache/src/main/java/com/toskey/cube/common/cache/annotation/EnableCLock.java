package com.toskey.cube.common.cache.annotation;

import com.toskey.cube.common.cache.config.RedissonClientConfiguration;
import com.toskey.cube.common.cache.config.RedissonLockConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用redisson分布式锁注解
 *
 * @author toskey
 * @version 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({ RedissonClientConfiguration.class, RedissonLockConfiguration.class })
public @interface EnableCLock {
}
