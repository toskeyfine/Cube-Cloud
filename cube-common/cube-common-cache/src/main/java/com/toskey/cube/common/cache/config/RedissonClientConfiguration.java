package com.toskey.cube.common.cache.config;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * RedissonConfiguration
 *
 * @author zhongxing
 * @date 2023/10/24 10:34
 */
@RequiredArgsConstructor
@EnableConfigurationProperties(RedissonClientProperties.class)
public class RedissonClientConfiguration {

    private final RedissonClientProperties redissonClientProperties;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%s", redissonClientProperties.getHost(), redissonClientProperties.getPort()))
                .setPassword(redissonClientProperties.getPassword())
                .setDatabase(redissonClientProperties.getDatabase());
        return Redisson.create(config);
    }

}
