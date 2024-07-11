package com.toskey.cube.common.security;

import com.toskey.cube.common.security.component.RequestAccessDeniedHandler;
import com.toskey.cube.common.security.component.RedisAuthorizationService;
import com.toskey.cube.common.security.component.RedisRegisteredClientRepository;
import com.toskey.cube.common.security.service.PasswordUserDetailsServiceImpl;
import com.toskey.cube.common.security.service.SmsUserDetailsServiceImpl;
import com.toskey.cube.common.security.service.CubeUserDetailsService;
import com.toskey.cube.service.sas.interfaces.service.RemoteUserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * SecurityAutoConfiguration
 *
 * @author toskey
 * @version 1.0.0
 */
public class SecurityAutoConfiguration {

    @Bean
    public CubeUserDetailsService passwordUserDetailsService(RedisTemplate<String, Object> redisTemplate,
                                                             RemoteUserService remoteUserService) {
        return new PasswordUserDetailsServiceImpl(redisTemplate, remoteUserService);
    }

    @Bean
    public CubeUserDetailsService smsUserDetailsService(RemoteUserService remoteUserService) {
        return new SmsUserDetailsServiceImpl(remoteUserService);
    }

    @Bean
    public RequestAccessDeniedHandler requestAccessDeniedHandler() {
        return new RequestAccessDeniedHandler();
    }

    @Bean
    public OAuth2AuthorizationService redisAuthorizationService(RedisTemplate<String, Object> redisTemplate) {
        return new RedisAuthorizationService(redisTemplate);
    }

    @Bean
    public RegisteredClientRepository redisRegisteredClientRepository(RedisTemplate<String, Object> redisTemplate) {
        return new RedisRegisteredClientRepository(redisTemplate);
    }
}
