package com.toskey.cube.common.cache.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * ClearCacheEvent
 *
 * @author zhongxing
 * @date 2023/8/10 17:05
 */
@Getter
@AllArgsConstructor(staticName = "of")
public class ClearCacheEvent {

    private final String key;

    private final Long delay;

    private final TimeUnit timeUnit;

}
