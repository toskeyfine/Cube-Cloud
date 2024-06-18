package com.toskey.cube.common.job.xxljob.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * xxl-job配置类
 *
 * @author toskey
 * @version 1.0.0
 */
@ConfigurationProperties(prefix = "cube.job.xxl")
public class XxlJobProperties {

    @Getter
    @Setter
    private AdminProperties admin = new AdminProperties();

    @Getter
    @Setter
    private ExecutorProperties executor = new ExecutorProperties();


    @Getter
    @Setter
    public static class AdminProperties {

        private String addresses;

    }

    @Getter
    @Setter
    public static class ExecutorProperties {

        private String accessToken;

        private String appname;

        private String address;

        private String ip;

        private Integer port = 9977;

        private String logPath = "logs/zxicet/xxl-job/jobhandler";

        private Integer logRetentionDays = 30;
    }
}
