package com.toskey.cube.common.resource.server.component;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import java.util.Date;

/**
 * AccessTokenRepository
 *
 * @author lis
 * @version 1.0
 * @description TODO
 * @date 2024/6/6 16:44
 */
@RequiredArgsConstructor
public class RedisTokenRepository implements PersistentTokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        String key = buildSeriesCacheKey(token.getSeries());
        PersistentRememberMeToken rememberMeToken = (PersistentRememberMeToken) redisTemplate.opsForValue().get(key);
        if (rememberMeToken != null) {
            throw new DataIntegrityViolationException("Series: " + token.getSeries() + " already exists.");
        } else {
            redisTemplate.opsForValue().set(key, token);
            redisTemplate.opsForValue().set(buildUsernameCacheKey(token.getUsername()), token.getSeries());
        }
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentRememberMeToken oldToken = getTokenForSeries(series);
        PersistentRememberMeToken newToken = new PersistentRememberMeToken(oldToken.getUsername(), series, tokenValue, new Date());
        redisTemplate.opsForValue().set(buildSeriesCacheKey(series), newToken);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        return (PersistentRememberMeToken) redisTemplate.opsForValue().get(buildSeriesCacheKey(seriesId));
    }

    @Override
    public void removeUserTokens(String username) {
        String series = (String) redisTemplate.opsForValue().getAndDelete(buildUsernameCacheKey(username));
        redisTemplate.delete(buildSeriesCacheKey(series));
    }

    private String buildUsernameCacheKey(String username) {
        return String.format("%s:persistent:username:%s", "", username);
    }

    private String buildSeriesCacheKey(String series) {
        return String.format("%s:persistent:series:%s", "", series);
    }
}
