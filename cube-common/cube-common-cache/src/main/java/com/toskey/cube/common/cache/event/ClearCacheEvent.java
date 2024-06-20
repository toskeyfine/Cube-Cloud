package com.toskey.cube.common.cache.event;

import java.util.concurrent.TimeUnit;

/**
 * ClearCacheEvent
 *
 * @author toskey
 * @version 1.0.0
 */
public record ClearCacheEvent(String key, Long delay, TimeUnit timeUnit) {

}
