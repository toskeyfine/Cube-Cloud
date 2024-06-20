package com.toskey.cube.service.config.portal;

import com.toskey.cube.common.resource.server.annotation.ResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * ConfigPortalApplication
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 15:59
 */
@EnableFeignClients(basePackages = "com.toskey.cube")
@ResourceServer
@EnableDiscoveryClient
@MapperScan(basePackages = "com.toskey.cube.service.config.business.domain.mapper")
@SpringBootApplication(scanBasePackages = "com.toskey.cube")
public class ConfigPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigPortalApplication.class, args);
    }

}
