package com.toskey.cube.gateway.captcha;

import com.anji.captcha.service.CaptchaCacheService;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * anji-plus验证码实现
 *
 * @author toskey
 * @version 1.0.0
 */
public class CaptchaCacheServiceImpl implements CaptchaCacheService {

	private static final String REDIS = "redis";

	private final StringRedisTemplate stringRedisTemplate;

	public CaptchaCacheServiceImpl(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	@Override
	public void set(String key, String value, long expiresInSeconds) {
		stringRedisTemplate.opsForValue().set(key, value, expiresInSeconds, TimeUnit.SECONDS);
	}

	@Override
	public boolean exists(String key) {
		return stringRedisTemplate.hasKey(key);
	}

	@Override
	public void delete(String key) {
		stringRedisTemplate.delete(key);
	}

	@Override
	public String get(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	@Override
	public String type() {
		return REDIS;
	}

}
