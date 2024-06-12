package com.toskey.cube.common.cache.component;

import com.toskey.cube.common.core.constant.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * redis cache 扩展cache name自动化配置
 */
@Slf4j
public class RedisAutoCacheManager extends RedisCacheManager {

	private static final String SPLIT_FLAG = "#";

	private static final int CACHE_LENGTH = 2;

	public RedisAutoCacheManager(RedisCacheWriter cacheWriter,
								 RedisCacheConfiguration defaultCacheConfiguration,
								 boolean allowInFlightCacheCreation,
								 Map<String, RedisCacheConfiguration> initialCacheConfigurations) {
		super(cacheWriter, defaultCacheConfiguration, allowInFlightCacheCreation, initialCacheConfigurations);
	}

	@NonNull
	@Override
	protected RedisCache createRedisCache(@NonNull String name, @Nullable RedisCacheConfiguration cacheConfig) {
		if (StringUtils.isBlank(name) || !name.contains(SPLIT_FLAG)) {
			return super.createRedisCache(name, cacheConfig);
		}

		String[] cacheArray = name.split(SPLIT_FLAG);
		if (cacheArray.length < CACHE_LENGTH) {
			return super.createRedisCache(name, cacheConfig);
		}

		if (cacheConfig != null) {
			Duration duration = DurationStyle.detectAndParse(cacheArray[1], ChronoUnit.SECONDS);
			cacheConfig = cacheConfig.entryTtl(duration);
		}
		return super.createRedisCache(cacheArray[0], cacheConfig);
	}

	/**
	 * 从上下文中获取租户ID，重写@Cacheable value 值
	 * @param name
	 * @return
	 */
	@Override
	public Cache getCache(String name) {
		if (name.startsWith(CacheConstants.CACHE_GLOBAL_PREFIX)) {
			return super.getCache(name);
		}
		return super.getCache(name);
	}

}
