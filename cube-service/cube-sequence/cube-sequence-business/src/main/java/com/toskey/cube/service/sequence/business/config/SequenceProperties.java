package com.toskey.cube.service.sequence.business.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * SequenceProperty
 *
 * @author toskey
 * @version 1.0.0
 */
@Configuration
@ConfigurationProperties(prefix = "cube.sequence")
public class SequenceProperties {

    @Getter
    @Setter
    private Long machineId = 1L;

    @Getter
    @Setter
    private Long datacenterId = 1L;

    @Getter
    @Setter
    private String sectionMode = "redis";

    @Getter
    @Setter
    private String snowflakeMode = "redis";

}
