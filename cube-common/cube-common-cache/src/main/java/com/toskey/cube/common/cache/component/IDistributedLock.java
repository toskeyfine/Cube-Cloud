package com.toskey.cube.common.cache.component;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁接口
 *
 * @author toskey
 * @version 1.0.0
 */
public interface IDistributedLock {

    CubeLock lock(String key);

    CubeLock lock(String key, long expire, TimeUnit timeUnit, boolean isFair);

    CubeLock tryLock(String key, long expire) throws Exception;

    CubeLock tryLock(String key, long tryExpire, long expire, TimeUnit timeUnit, boolean isFair) throws Exception;

    void unlock(Object lock);

    default void unlock(CubeLock lock) {
        if (lock != null) {
            unlock(lock.getLock());
        }
    }

}
