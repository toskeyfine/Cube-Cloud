package com.toskey.cube.common.cache.config;

import com.toskey.cube.common.cache.event.ClearCacheEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisClearCacheDelayConfiguration
 *
 * @author toskey
 * @version 1.0.0
 */
public class RedisClearCacheDelayConfiguration {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisClearCacheDelayConfiguration(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public ClearCacheEventListener clearCacheEventListener() {
        return new ClearCacheEventListener(redisTemplate);
    }

}