package com.toskey.cube.common.security.component;

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
//        throw new UnsupportedOperationException("Unsupported operation: RedisRegisteredClientRepository.save()");
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
        return String.format("%s:%s:%s", CacheConstants.CACHE_GLOBAL_PREFIX, CacheConstants.CACHE_REGISTERED_CLIENT_KEY, clientId);
    }
}
