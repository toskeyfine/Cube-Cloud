package com.toskey.cube.service.config.business.Initializer;

import com.toskey.cube.common.core.constant.CacheConstants;
import com.toskey.cube.service.config.business.domain.entity.OAuth2Client;
import com.toskey.cube.service.config.business.service.OAuth2ClientService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * ClientInitializer
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/18 15:55
 */
@Component
public class OAuth2ClientInitializer implements InitializingBean {

    private final OAuth2ClientService clientService;

    private final RedisTemplate<String, Object> redisTemplate;

    public OAuth2ClientInitializer(OAuth2ClientService clientService, RedisTemplate<String, Object> redisTemplate) {
        this.clientService = clientService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        clientService.list().stream()
                .map(OAuth2Client::toRegisteredClient)
                .forEach(registeredClient -> {
                    String cacheKey = String.format("%s:%s:%s", CacheConstants.CACHE_GLOBAL_PREFIX,
                            CacheConstants.CACHE_REGISTERED_CLIENT_KEY, registeredClient.getClientId());
                    redisTemplate.opsForValue().set(cacheKey, registeredClient);
                });
    }

}
