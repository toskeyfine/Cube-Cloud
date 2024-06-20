package com.toskey.cube.common.cache.event;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * ClearCacheEventListener
 *
 * @author toskey
 * @version 1.0.0
 */
public class ClearCacheEventListener {

    private final RedisTemplate<String, Object> redisTemplate;

    public ClearCacheEventListener(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Async
    @Order
    @EventListener(ClearCacheEvent.class)
    public void clearCache(ClearCacheEvent event) throws InterruptedException {
        Long delay = event.delay();
        if (Objects.isNull(delay)) {
            delay = 500L;
        }
        TimeUnit timeUnit = event.timeUnit();
        if (Objects.isNull(timeUnit)) {
            timeUnit = TimeUnit.MILLISECONDS;
        }

        timeUnit.sleep(delay);

        redisTemplate.delete(event.key());
    }
}
