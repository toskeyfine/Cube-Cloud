package com.toskey.cube.common.cache.component;

import lombok.AllArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁实现
 *
 * @author lish
 * @date 2023/10/24 10:40
 * @code
 * private final IDistributedLock distributedLock;<br>
 *      try (ZxLock lock = distributedLock.lock("key")) {
 *      <br>
 *      do something...
 *      <br>
 *      }
 *      <br>
 *      catch(Exception e) {
 *      <br>
 *      throw e;
 *      <br>
 *      }
 */
@AllArgsConstructor
public class DefaultDistributedLock implements IDistributedLock {

    private final RedissonClient redissonClient;

    @Override
    public CubeLock lock(String key) {
        return this.lock(key, 0L, TimeUnit.SECONDS, false);
    }

    @Override
    public CubeLock lock(String key, long expire, TimeUnit timeUnit, boolean isFair) {
        RLock lock = getLock(key, isFair);
        if (expire > 0L) {
            lock.lock(expire, timeUnit);
        } else {
            lock.lock();
        }
        return new CubeLock(lock, this);
    }

    @Override
    public CubeLock tryLock(String key, long expire) throws Exception {
        return tryLock(key, expire, 0L, TimeUnit.SECONDS, false);
    }

    private RLock getLock(String key, boolean isFair) {
        if (isFair) {
            return redissonClient.getFairLock(key);
        }
        return redissonClient.getLock(key);
    }

    @Override
    public CubeLock tryLock(String key, long tryExpire, long expire, TimeUnit timeUnit, boolean isFair) throws Exception {
        RLock lock = getLock(key, isFair);
        boolean lockAcquired;
        if (expire > 0L) {
            lockAcquired = lock.tryLock(tryExpire, expire, timeUnit);
        } else {
            lockAcquired = lock.tryLock(tryExpire, timeUnit);
        }
        if (lockAcquired) {
            return new CubeLock(lock, this);
        }
        return null;
    }

    @Override
    public void unlock(Object lock) {
        if (!(lock instanceof RLock rLock)) {
            throw new IllegalArgumentException("Invalid lock object.");
        }
        if (rLock.isLocked()) {
            try {
                rLock.unlock();
            } catch (IllegalMonitorStateException e) {
                e.printStackTrace();
            }
        }
    }

}
