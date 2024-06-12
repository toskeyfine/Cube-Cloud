package com.toskey.cube.common.resource.server.config;

import com.toskey.cube.common.resource.server.component.CubePasswordUserDetailsServiceImpl;
import com.toskey.cube.common.resource.server.component.CubeSmsUserDetailsServiceImpl;
import com.toskey.cube.common.resource.server.component.CubeUserDetailsService;
import org.springframework.context.annotation.Bean;

/**
 * SecurityAutoConfiguration
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 17:45
 */
public class SecurityAutoConfiguration {

    @Bean
    public CubeUserDetailsService passwordUserDetailsService() {
        return new CubePasswordUserDetailsServiceImpl();
    }

    @Bean
    public CubeUserDetailsService smsUserDetailsService() {
        return new CubeSmsUserDetailsServiceImpl();
    }

}
