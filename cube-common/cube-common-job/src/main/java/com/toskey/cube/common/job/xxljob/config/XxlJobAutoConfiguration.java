package com.toskey.cube.common.job.xxljob.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * xxl-job自动装配
 *
 * @author toskey
 * @version 1.0.0
 */
@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
@EnableConfigurationProperties(XxlJobProperties.class)
public class XxlJobAutoConfiguration {

    @Bean
    public XxlJobSpringExecutor xxlJobSpringExecutor(XxlJobProperties xxlJobProperties, Environment environment) {
        XxlJobSpringExecutor executor = new XxlJobSpringExecutor();
        XxlJobProperties.ExecutorProperties executorProperties = xxlJobProperties.getExecutor();
        String appName = executorProperties.getAppname();
        if (!StringUtils.hasText(appName)) {
            // 默认名
            appName = environment.getProperty("spring.application.name");
        }
        executor.setAppname(appName);
        executor.setAddress(executorProperties.getAddress());
        executor.setIp(executorProperties.getIp());
        executor.setPort(executorProperties.getPort());
        executor.setAccessToken(executorProperties.getAccessToken());
        executor.setLogPath(executorProperties.getLogPath());
        executor.setLogRetentionDays(executorProperties.getLogRetentionDays());
        executor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
        return executor;
    }
}
