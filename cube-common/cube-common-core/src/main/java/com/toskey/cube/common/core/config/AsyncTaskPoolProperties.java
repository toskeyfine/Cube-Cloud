package com.toskey.cube.common.core.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * AsyncTaskPoolProperties
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 10:29
 */
@Getter
@ConfigurationProperties("cube.task.pool")
@AllArgsConstructor
public class AsyncTaskPoolProperties {

    private final int corePoolSize;

    private final int maxPoolSize;

    private final int keepAliveSeconds;

    private final int queueCapacity;

}
