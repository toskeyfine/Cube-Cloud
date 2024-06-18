package com.toskey.cube.common.cache.component;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * ZxLock
 *
 * @author toskey
 * @version 1.0.0
 */
@Getter
@AllArgsConstructor
public class CubeLock implements AutoCloseable {


    private final Object lock;

    private final IDistributedLock distributedLock;

    @Override
    public void close() throws Exception {
        if (Objects.nonNull(lock)) {
            distributedLock.unlock(this);
        }
    }
}
