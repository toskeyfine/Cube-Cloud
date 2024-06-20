package com.toskey.cube.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * GatewayApplication
 *
 * @author toskey
 * @version 1.0.0
 */
@EnableFeignClients(basePackages = "com.toskey.cube")
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.toskey.cube")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
