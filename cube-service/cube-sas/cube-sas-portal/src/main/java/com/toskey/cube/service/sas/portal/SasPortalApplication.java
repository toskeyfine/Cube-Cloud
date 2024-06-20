package com.toskey.cube.service.sas.portal;

import com.toskey.cube.common.resource.server.annotation.ResourceServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * SasPortalApplication
 *
 * @author toskey
 * @version 1.0.0
 */
@ResourceServer
@EnableFeignClients(basePackages = "com.toskey.cube")
@EnableDiscoveryClient
@MapperScan(basePackages = "com.toskey.cube.service.sas.business.domain.mapper")
@SpringBootApplication(scanBasePackages = "com.toskey.cube")
public class SasPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SasPortalApplication.class, args);
    }

}
