package com.toskey.cube.common.cache.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * ClearCacheEventListener
 *
 * @author zhongxing
 * @date 2023/8/10 17:06
 */
@RequiredArgsConstructor
public class ClearCacheEventListener {

    private final RedisTemplate<String, Object> redisTemplate;

    @Async
    @Order
    @EventListener(ClearCacheEvent.class)
    public void clearCache(ClearCacheEvent event) throws InterruptedException {
        Long delay = event.getDelay();
        if (Objects.isNull(delay)) {
            delay = 500L;
        }
        TimeUnit timeUnit = event.getTimeUnit();
        if (Objects.isNull(timeUnit)) {
            timeUnit = TimeUnit.MILLISECONDS;
        }

        timeUnit.sleep(delay);

        redisTemplate.delete(event.getKey());
    }
}
