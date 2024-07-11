package com.toskey.cube.common.cache.config;

import com.toskey.cube.common.core.util.StringUtils;
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
 * @author toskey
 * @version 1.0.0
 */
@EnableConfigurationProperties(RedissonClientProperties.class)
public class RedissonClientConfiguration {

    private final RedissonClientProperties redissonClientProperties;

    public RedissonClientConfiguration(RedissonClientProperties redissonClientProperties) {
        this.redissonClientProperties = redissonClientProperties;
    }

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%s", redissonClientProperties.getHost(), redissonClientProperties.getPort()))
                .setDatabase(redissonClientProperties.getDatabase());
        if (StringUtils.isNotBlank(redissonClientProperties.getPassword())) {
            config.useSingleServer().setPassword(redissonClientProperties.getPassword());
        }
        return Redisson.create(config);
    }

}
