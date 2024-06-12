package com.toskey.cube.common.resource.server.component;

import com.toskey.cube.common.core.constant.CacheConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * RedisRegisteredClientRepository
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 17:02
 */
@RequiredArgsConstructor
public class RedisRegisteredClientRepository implements RegisteredClientRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(RegisteredClient registeredClient) {
        String cacheKey = registeredClient.getClientId();
        RegisteredClient cache = (RegisteredClient) redisTemplate.opsForValue().get(cacheKey);
        if (cache != null) {
            throw new DataIntegrityViolationException("Registered client: " + registeredClient.getClientId() + " already exists.");
        } else {
            redisTemplate.opsForValue().set(cacheKey, registeredClient);
        }
    }

    @Override
    public RegisteredClient findById(String id) {
        throw new UnsupportedOperationException("Unsupported operation: RedisRegisteredClientRepository.findById()");
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return (RegisteredClient) redisTemplate.opsForValue().get(buildCacheKey(clientId));
    }

    private String buildCacheKey(String clientId) {
        return String.format("%s:client:registered:%s", CacheConstants.CACHE_GLOBAL_PREFIX, clientId);
    }
}
