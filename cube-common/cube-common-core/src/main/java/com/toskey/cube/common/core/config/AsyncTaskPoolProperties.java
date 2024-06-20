package com.toskey.cube.common.core.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * AsyncTaskPoolProperties
 *
 * @author toskey
 * @version 1.0
 */
@Getter
@ConfigurationProperties("cube.task.pool")
@AllArgsConstructor
public class AsyncTaskPoolProperties {

    private final int corePoolSize = 4;

    private final int maxPoolSize = 8;

    private final int keepAliveSeconds = 60;

    private final int queueCapacity = 20;

}
