package com.toskey.cube.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * AcsApplication
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/14 10:33
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.toskey.cube", exclude = { DataSourceAutoConfiguration.class })
public class AcsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcsApplication.class, args);
    }

}
