package com.toskey.cube.service.log.portal;

import com.toskey.cube.common.resource.server.annotation.ResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * LogPortalApplication
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:56
 */
@ResourceServer
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.toskey.cube")
@MapperScan(basePackages = "com.toskey.cube.service.log.business.domain.mapper")
@SpringBootApplication(scanBasePackages = "com.toskey.cube")
public class LogPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogPortalApplication.class, args);
    }

}
