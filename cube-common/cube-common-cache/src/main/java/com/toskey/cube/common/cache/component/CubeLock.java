package com.toskey.cube.common.cache.component;


import java.util.Objects;

/**
 * ZxLock
 *
 * @author toskey
 * @version 1.0.0
 */
public class CubeLock implements AutoCloseable {

    private final Object lock;

    private final IDistributedLock distributedLock;

    public CubeLock(Object lock, IDistributedLock distributedLock) {
        this.lock = lock;
        this.distributedLock = distributedLock;
    }

    public Object getLock() {
        return lock;
    }

    public IDistributedLock getDistributedLock() {
        return distributedLock;
    }

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(lock)) {
            distributedLock.unlock(this);
        }
    }

}
