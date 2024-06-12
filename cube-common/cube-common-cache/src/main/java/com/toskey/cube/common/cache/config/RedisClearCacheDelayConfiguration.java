package com.toskey.cube.common.cache.config;

import com.toskey.cube.common.cache.event.ClearCacheEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RedisClearCacheDelayConfiguration
 *
 * @author zhongxing
 * @date 2023/8/10 17:19
 */
@RequiredArgsConstructor
public class RedisClearCacheDelayConfiguration {

    private final RedisTemplate<String, Object> redisTemplate;

    @Bean
    @ConditionalOnMissingBean
    public ClearCacheEventListener clearCacheEventListener() {
        return new ClearCacheEventListener(redisTemplate);
    }

}