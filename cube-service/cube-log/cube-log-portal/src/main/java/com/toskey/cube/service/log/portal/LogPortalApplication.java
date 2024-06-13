package com.toskey.cube.service.log.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * LogPortalApplication
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/13 17:56
 */
@MapperScan(basePackages = "com.toskey.cube.service.log.business.domain.mapper")
@SpringBootApplication(scanBasePackages = "com.toskey.cube")
public class LogPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogPortalApplication.class, args);
    }

}
