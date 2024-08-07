package com.toskey.cube.service.sequence.portal;

import com.toskey.cube.common.resource.server.annotation.ResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * SequencePortalApplication
 *
 * @author toskey
 * @version 1.0.0
 */
@ResourceServer
@EnableFeignClients(basePackages = "com.toskey.cube")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.toskey.cube.service.sequence.business.domain.mapper")
@SpringBootApplication(scanBasePackages = "com.toskey.cube")
public class SequencePortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SequencePortalApplication.class, args);
    }

}
