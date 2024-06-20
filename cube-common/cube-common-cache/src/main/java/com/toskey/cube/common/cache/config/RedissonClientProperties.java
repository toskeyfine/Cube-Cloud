package com.toskey.cube.common.cache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

/**
 * RedissonClientProperties
 *
 * @author toskey
 * @version 1.0.0
 */
@Primary
@ConfigurationProperties(prefix = "spring.data.redis")
public record RedissonClientProperties(String host, String port, String password, int database) {

}
