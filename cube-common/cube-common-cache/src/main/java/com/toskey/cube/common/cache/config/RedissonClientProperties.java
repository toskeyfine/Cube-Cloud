package com.toskey.cube.common.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

/**
 * RedissonClientProperties
 *
 * @author zhongxing
 * @date 2023/10/24 10:47
 */
@Primary
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedissonClientProperties {

    private String host;

    private String port;

    private String password;

    private Integer database = 0;

}
