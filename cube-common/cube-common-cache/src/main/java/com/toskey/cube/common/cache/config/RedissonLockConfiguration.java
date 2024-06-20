package com.toskey.cube.common.cache.config;

import com.toskey.cube.common.cache.aspect.CLockAspect;
import com.toskey.cube.common.cache.component.DefaultDistributedLock;
import com.toskey.cube.common.cache.component.IDistributedLock;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

/**
 * 分布式锁配置类
 *
 * @author toskey
 * @version 1.0.0
 */
public class RedissonLockConfiguration {

    @Bean
    @ConditionalOnBean(RedissonClient.class)
    public IDistributedLock distributedLock(RedissonClient redissonClient) {
        return new DefaultDistributedLock(redissonClient);
    }

    @Bean
    public CLockAspect distributedLockAspect(IDistributedLock distributedLock) {
        return new CLockAspect(distributedLock);
    }

}
