package com.toskey.cube.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * AcsApplication
 *
 * @author toskey
 * @version 1.0.0
 */
@EnableFeignClients(basePackages = "com.toskey.cube")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.toskey.cube", exclude = { DataSourceAutoConfiguration.class })
public class AcsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcsApplication.class, args);
    }

}
