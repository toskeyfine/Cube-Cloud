package com.toskey.cube.common.cache.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

/**
 * RedissonClientProperties
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedissonClientProperties {

    private String host;

    private int port;

    private String password;

    private int database;

}
