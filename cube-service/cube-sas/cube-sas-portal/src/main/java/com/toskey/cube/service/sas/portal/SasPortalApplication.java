package com.toskey.cube.service.sas.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SasPortalApplication
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/7 15:57
 */
@MapperScan(basePackages = "com.toskey.cube.service.sas.business.domain.mapper")
@SpringBootApplication(scanBasePackages = "com.toskey.cube")
public class SasPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(SasPortalApplication.class, args);
    }

}
