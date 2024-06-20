package com.toskey.cube.common.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * AsyncTaskPoolProperties
 *
 * @author toskey
 * @version 1.0.0
 */
@ConfigurationProperties("cube.task.pool")
public class AsyncTaskPoolProperties {

    private int corePoolSize = 4;

    private int maxPoolSize = 8;

    private int keepAliveSeconds = 60;

    private int queueCapacity = 20;

    public AsyncTaskPoolProperties(int corePoolSize, int maxPoolSize, int keepAliveSeconds, int queueCapacity) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.keepAliveSeconds = keepAliveSeconds;
        this.queueCapacity = queueCapacity;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }
}
